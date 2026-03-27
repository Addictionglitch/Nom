package com.example.nom

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.nom.core.data.local.OfflinePlantCache
import com.example.nom.workers.SpiritUpdateWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * The main application class for the Nom app.
 *
 * This class is responsible for setting up Hilt, WorkManager, and the offline plant cache.
 */
@HiltAndroidApp
class NomApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var offlinePlantCache: OfflinePlantCache

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        setupRecurringWork()
        loadOfflinePlantCache()
    }

    private fun setupRecurringWork() {
        val repeatingRequest = PeriodicWorkRequestBuilder<SpiritUpdateWorker>(2, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "spirit-update-work",
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    private fun loadOfflinePlantCache() {
        CoroutineScope(Dispatchers.Main).launch {
            offlinePlantCache.loadOfflinePlants()
        }
    }
}
