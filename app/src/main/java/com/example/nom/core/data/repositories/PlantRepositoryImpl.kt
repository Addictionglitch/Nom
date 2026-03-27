package com.example.nom.core.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.nom.BuildConfig
import com.example.nom.core.data.local.PlantDao
import com.example.nom.core.data.local.ScanHistoryDao
import com.example.nom.core.data.local.toDomain
import com.example.nom.core.data.local.toEntity
import com.example.nom.core.data.remote.plantid.IdentificationRequest
import com.example.nom.core.data.remote.plantid.PlantIdApiService
import com.example.nom.core.data.remote.plantid.PlantIdMapper
import com.example.nom.core.domain.models.Plant
import com.example.nom.core.domain.models.ScanResult
import com.example.nom.core.domain.models.SpiritEmotion
import com.example.nom.core.domain.repositories.PlantRepository
import com.example.nom.core.utils.Result
import com.example.nom.core.utils.StatCalculator
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor(
    private val plantIdApiService: PlantIdApiService,
    private val plantDao: PlantDao,
    private val scanHistoryDao: ScanHistoryDao,
    @ApplicationContext private val context: Context
) : PlantRepository {

    private val gson = Gson()

    override fun observePlants(): Flow<List<Plant>> {
        return plantDao.getAllPlants().map { entities -> entities.map { it.toDomain() } }
    }

    override fun getPlantById(id: String): Flow<Plant> {
        return plantDao.getPlantById(id).map { it.toDomain() }
    }

    override suspend fun scanPlant(imageBase64: String, imageUri: String): Result<ScanResult> {
        if (isOnline()) {
            return try {
                val request = IdentificationRequest(images = listOf(imageBase64))
                val response = plantIdApiService.identifyPlant(BuildConfig.PLANT_ID_API_KEY, request)
                val suggestion = response.result.classification.suggestions.first()
                val plant = PlantIdMapper.toDomain(suggestion, imageUri)

                val isNewDiscovery = plantDao.getPlantByScientificName(plant.scientificName) == null
                val xpGained = StatCalculator.calculateFeedingXp(plant, isNewDiscovery)
                val spiritReaction = if (plant.isToxic) SpiritEmotion.QUEASY
                    else if (isNewDiscovery) SpiritEmotion.EXCITED
                    else SpiritEmotion.HAPPY

                plantDao.insertAll(listOf(plant.toEntity()))

                val scanResult = ScanResult(
                    id = suggestion.id,
                    plant = plant,
                    confidence = suggestion.probability,
                    isNewDiscovery = isNewDiscovery,
                    xpGained = xpGained,
                    spiritReaction = spiritReaction,
                    timestamp = System.currentTimeMillis()
                )

                val plantJson = gson.toJson(plant)
                scanHistoryDao.insertScan(scanResult.toEntity(plantJson))
                Result.Success(scanResult)
            } catch (e: Exception) {
                Timber.e(e, "Error scanning plant online.")
                Result.Error(e)
            }
        } else {
            Timber.d("Offline: Scan queued for later processing.")
            return Result.Error(Exception("Offline. Scan queued for when connectivity returns."))
        }
    }

    private fun isOnline(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
