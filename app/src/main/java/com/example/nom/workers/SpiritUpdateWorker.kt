package com.example.nom.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.nom.core.domain.repositories.SpiritRepository
import com.example.nom.core.domain.usecases.DecaySpiritStatsUseCase
import com.example.nom.notifications.NotificationBuilder
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.util.concurrent.TimeUnit

@HiltWorker
class SpiritUpdateWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val spiritRepository: SpiritRepository,
    private val decaySpiritStatsUseCase: DecaySpiritStatsUseCase,
    private val notificationBuilder: NotificationBuilder
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            Timber.d("SpiritUpdateWorker started.")
            val spirit = spiritRepository.getSpirit()
            val currentTime = System.currentTimeMillis()
            val hoursElapsed = TimeUnit.MILLISECONDS.toHours(currentTime - spirit.lastFedTimestamp)

            if (hoursElapsed > 0) {
                val updatedSpirit = decaySpiritStatsUseCase(spirit, hoursElapsed.toInt())
                spiritRepository.saveSpirit(updatedSpirit)
                Timber.d("Spirit stats decayed after %d hours.", hoursElapsed)

                if (updatedSpirit.happiness < 0.3f || updatedSpirit.health < 0.3f) {
                    notificationBuilder.buildCuriosityNotification()
                    Timber.d("Spirit stats are low, sending curiosity notification.")
                }
            } else {
                Timber.d("Not enough time has passed to decay spirit stats.")
            }

            Result.success()
        } catch (e: Exception) {
            Timber.e(e, "Error in SpiritUpdateWorker.")
            Result.failure()
        }
    }
}
