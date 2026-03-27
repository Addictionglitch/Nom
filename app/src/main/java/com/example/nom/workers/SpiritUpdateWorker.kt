package com.example.nom.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.nom.core.data.local.SecurePreferences
import com.example.nom.core.domain.repositories.SpiritRepository
import com.example.nom.core.domain.usecases.DecaySpiritStatsUseCase
import com.example.nom.notifications.NotificationBuilder
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.util.concurrent.TimeUnit

@HiltWorker
class SpiritUpdateWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val spiritRepository: SpiritRepository,
    private val decaySpiritStatsUseCase: DecaySpiritStatsUseCase,
    private val notificationBuilder: NotificationBuilder,
    private val securePreferences: SecurePreferences
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            Timber.d("SpiritUpdateWorker started.")
            val spirit = spiritRepository.observeSpirit().first() ?: return Result.success()
            val currentTime = System.currentTimeMillis()
            val hoursElapsed = TimeUnit.MILLISECONDS.toHours(currentTime - spirit.lastFedTimestamp).toFloat()

            if (hoursElapsed > 0f) {
                decaySpiritStatsUseCase(spirit, hoursElapsed)
                Timber.d("Spirit stats decayed after %.1f hours.", hoursElapsed)

                val updated = spiritRepository.observeSpirit().first()
                if (updated != null && (updated.happiness < 0.3f || updated.hunger < 0.3f)) {
                    notificationBuilder.buildCuriosityNotification(securePreferences)
                    Timber.d("Spirit stats are low, sending curiosity notification.")
                }
            }

            Result.success()
        } catch (e: Exception) {
            Timber.e(e, "Error in SpiritUpdateWorker.")
            Result.failure()
        }
    }
}
