package com.example.nom.core.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.nom.BuildConfig
import com.example.nom.core.data.local.OfflinePlantCache
import com.example.nom.core.data.local.PlantDao
import com.example.nom.core.data.local.ScanHistoryDao
import com.example.nom.core.data.local.toDomain
import com.example.nom.core.data.local.toEntity
import com.example.nom.core.data.remote.plantid.IdentificationRequest
import com.example.nom.core.data.remote.plantid.PlantIdApiService
import com.example.nom.core.data.remote.plantid.PlantIdMapper
import com.example.nom.core.domain.models.Plant
import com.example.nom.core.domain.models.ScanResult
import com.example.nom.core.domain.repositories.PlantRepository
import com.example.nom.core.domain.services.StatCalculator
import com.example.nom.core.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor(
    private val plantIdApiService: PlantIdApiService,
    private val plantDao: PlantDao,
    private val scanHistoryDao: ScanHistoryDao,
    private val offlinePlantCache: OfflinePlantCache,
    private val statCalculator: StatCalculator,
    private val context: Context
) : PlantRepository {

    override fun observePlants(): Flow<List<Plant>> {
        return plantDao.getAllPlants().map { it.map { it.toDomain() } }
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
                val xpGained = statCalculator.calculateFeedingXp(plant, isNewDiscovery)
                val spiritReaction = statCalculator.determineSpiritReaction()

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

                scanHistoryDao.insertScan(scanResult.toEntity(isSynced = true))
                Result.Success(scanResult)
            } catch (e: Exception) {
                Timber.e(e, "Error scanning plant online.")
                Result.Error(e)
            }
        } else {
            Timber.d("Offline: Attempting to match against cache or queueing scan.")
            val offlineMatch = offlinePlantCache.match(imageBase64)
            if (offlineMatch != null) {
                val scanResult = ScanResult(
                    id = offlineMatch.id,
                    plant = offlineMatch,
                    confidence = 1.0,
                    isNewDiscovery = false, // Cannot be a new discovery if it's from the cache
                    xpGained = 0, // No XP for offline cache match
                    spiritReaction = com.example.nom.core.domain.models.SpiritEmotion.NEUTRAL,
                    timestamp = System.currentTimeMillis()
                )
                scanHistoryDao.insertScan(scanResult.toEntity(isSynced = true)) // Already in our DB
                return Result.Success(scanResult)
            } else {
                val scanHistoryEntity = ScanResult(
                    id = "offline_${System.currentTimeMillis()}",
                    plant = Plant(scientificName = "Unknown", commonName = "Unknown", imageUrl = imageUri),
                    confidence = 0.0,
                    isNewDiscovery = false,
                    xpGained = 0,
                    spiritReaction = com.example.nom.core.domain.models.SpiritEmotion.NEUTRAL,
                    timestamp = System.currentTimeMillis()
                ).toEntity(isSynced = false)

                scanHistoryDao.insertScan(scanHistoryEntity)
                return Result.Error(Exception("Offline and no match found. Scan queued."))
            }
        }
    }

    private fun isOnline(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
