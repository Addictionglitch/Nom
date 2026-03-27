package com.example.nom.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.nom.core.data.local.ScanHistoryDao
import com.example.nom.core.domain.repositories.PlantRepository
import com.example.nom.core.domain.usecases.FeedSpiritUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

@HiltWorker
class ScanQueueWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val scanHistoryDao: ScanHistoryDao,
    private val plantRepository: PlantRepository,
    private val feedSpiritUseCase: FeedSpiritUseCase
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val unsyncedScans = scanHistoryDao.getUnsyncedScans()
                Timber.d("Found %d unsynced scans to process.", unsyncedScans.size)

                unsyncedScans.forEach { scanEntity ->
                    // This assumes you have a way to get the image data for the scan
                    // For this example, I'll assume the imageUri is a Base64 string for simplicity
                    // In a real app, you might need to read the file from the URI
                    val imageBase64 = scanEntity.plant.imageUrl // Placeholder
                    val result = plantRepository.scanPlant(imageBase64, scanEntity.plant.imageUrl)

                    if (result is com.example.nom.core.utils.Result.Success) {
                        val scanResult = result.data
                        feedSpiritUseCase(scanResult)
                        val updatedEntity = scanEntity.copy(isSynced = true)
                        scanHistoryDao.insertScan(updatedEntity)
                        Timber.d("Successfully synced and processed scan: %s", scanEntity.id)
                    } else {
                        Timber.e("Failed to sync scan: %s", scanEntity.id)
                    }
                }
                Result.success()
            } catch (e: Exception) {
                Timber.e(e, "Error processing scan queue.")
                Result.failure()
            }
        }
    }
}
