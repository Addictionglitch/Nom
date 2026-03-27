package com.example.nom.core.data.repositories

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
import com.example.nom.core.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of the PlantRepository.
 *
 * @param plantIdApiService The service for the Plant.id API.
 * @param plantDao The DAO for accessing plant data.
 * @param scanHistoryDao The DAO for accessing scan history data.
 * @param offlinePlantCache The cache for offline plants.
 */
class PlantRepositoryImpl @Inject constructor(
    private val plantIdApiService: PlantIdApiService,
    private val plantDao: PlantDao,
    private val scanHistoryDao: ScanHistoryDao,
    private val offlinePlantCache: OfflinePlantCache
) : PlantRepository {

    override fun observePlants(): Flow<List<Plant>> {
        return plantDao.getAllPlants().map { it.map { it.toDomain() } }
    }

    override suspend fun scanPlant(imageBase64: String, imageUri: String): Result<ScanResult> {
        if (isOnline()) {
            return try {
                val request = IdentificationRequest(images = listOf(imageBase64))
                val response = plantIdApiService.identifyPlant(BuildConfig.PLANT_ID_API_KEY, request)
                val suggestion = response.result.classification.suggestions.first()
                val plant = PlantIdMapper.toDomain(suggestion, imageUri)
                plantDao.insertAll(listOf(plant.toEntity()))
                val scanResult = ScanResult(
                    id = suggestion.id,
                    plant = plant,
                    confidence = suggestion.probability,
                    isNewDiscovery = true, // Placeholder
                    xpGained = 0, // Placeholder
                    spiritReaction = com.example.nom.core.domain.models.SpiritEmotion.HAPPY, // Placeholder
                    timestamp = System.currentTimeMillis()
                )
                scanHistoryDao.insertScan(scanResult.toEntity())
                Result.Success(scanResult)
            } catch (e: Exception) {
                Result.Error(e)
            }
        } else {
            // TODO: Implement offline logic with offlinePlantCache and scan history queue
            return Result.Error(Exception("Offline"))
        }
    }

    private fun isOnline(): Boolean {
        // Placeholder for network connectivity check
        return true
    }
}
