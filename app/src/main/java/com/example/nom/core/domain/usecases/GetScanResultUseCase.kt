package com.example.nom.core.domain.usecases

import com.example.nom.core.data.local.PlantDao
import com.example.nom.core.data.local.ScanHistoryDao
import com.example.nom.core.data.local.toDomain
import com.example.nom.core.domain.models.ScanResult
import com.example.nom.core.utils.Result
import javax.inject.Inject

/**
 * Use case for getting a scan result by its ID.
 *
 * @param scanHistoryDao The DAO for accessing scan history data.
 * @param plantDao The DAO for accessing plant data.
 */
class GetScanResultUseCase @Inject constructor(
    private val scanHistoryDao: ScanHistoryDao,
    private val plantDao: PlantDao
) {

    /**
     * Invokes the use case to get a scan result by its ID.
     * @param id The ID of the scan result.
     * @return A Result containing the ScanResult.
     */
    suspend operator fun invoke(id: Long): Result<ScanResult> {
        return try {
            val scanHistoryEntity = scanHistoryDao.getScanById(id)
            if (scanHistoryEntity != null) {
                val plantEntity = plantDao.getPlantById(scanHistoryEntity.plantId)
                if (plantEntity != null) {
                    Result.Success(scanHistoryEntity.toDomain(plantEntity.toDomain()))
                } else {
                    Result.Error(Exception("Plant not found"))
                }
            } else {
                Result.Error(Exception("Scan not found"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
