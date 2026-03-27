package com.example.nom.core.domain.usecases

import com.example.nom.core.data.local.ScanHistoryDao
import com.example.nom.core.data.local.toDomain
import com.example.nom.core.domain.models.Plant
import com.example.nom.core.domain.models.ScanResult
import com.example.nom.core.utils.Result
import com.google.gson.Gson
import javax.inject.Inject

class GetScanResultUseCase @Inject constructor(
    private val scanHistoryDao: ScanHistoryDao,
    private val gson: Gson
) {
    suspend operator fun invoke(id: Long): Result<ScanResult> {
        return try {
            val entity = scanHistoryDao.getScanById(id)
            if (entity != null) {
                val plant = gson.fromJson(entity.plantSnapshot, Plant::class.java)
                Result.Success(entity.toDomain(plant))
            } else {
                Result.Error(Exception("Scan not found"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
