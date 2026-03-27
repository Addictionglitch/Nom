package com.example.nom.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.nom.core.data.local.ScanHistoryDao
import com.example.nom.core.data.local.toDomain
import com.example.nom.core.domain.models.Plant
import com.example.nom.core.domain.repositories.SpiritRepository
import com.example.nom.core.domain.usecases.FeedSpiritUseCase
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import timber.log.Timber

@HiltWorker
class ScanQueueWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val scanHistoryDao: ScanHistoryDao,
    private val spiritRepository: SpiritRepository,
    private val feedSpiritUseCase: FeedSpiritUseCase,
    private val gson: Gson
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val unsyncedScans = scanHistoryDao.getUnsyncedScans()
                Timber.d("Found %d unsynced scans to process.", unsyncedScans.size)

                unsyncedScans.forEach { scanEntity ->
                    try {
                        val plant = gson.fromJson(scanEntity.plantSnapshot, Plant::class.java)
                        val scanResult = scanEntity.toDomain(plant)
                        val spirit = spiritRepository.observeSpirit().first()
                        if (spirit != null) {
                            feedSpiritUseCase(spirit, scanResult.plant, scanResult.isNewDiscovery)
                        }
                        scanHistoryDao.markScansAsSynced(listOf(scanEntity.id))
                        Timber.d("Successfully processed scan: %d", scanEntity.id)
                    } catch (e: Exception) {
                        Timber.e(e, "Failed to process scan: %d", scanEntity.id)
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
