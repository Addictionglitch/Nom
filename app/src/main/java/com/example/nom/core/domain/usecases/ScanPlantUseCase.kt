package com.example.nom.core.domain.usecases

import com.example.nom.analytics.AnalyticsTracker
import com.example.nom.core.domain.models.ScanResult
import com.example.nom.core.domain.repositories.PlantRepository
import com.example.nom.core.domain.repositories.SpiritRepository
import com.example.nom.core.utils.Result
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

/**
 * Use case for scanning a plant.
 *
 * @param plantRepository The repository for managing plants.
 * @param spiritRepository The repository for managing the Spirit.
 * @param feedSpiritUseCase The use case for feeding the Spirit.
 * @param evolveSpiritUseCase The use case for evolving the Spirit.
 * @param analyticsTracker The analytics tracker.
 */
class ScanPlantUseCase @Inject constructor(
    private val plantRepository: PlantRepository,
    private val spiritRepository: SpiritRepository,
    private val feedSpiritUseCase: FeedSpiritUseCase,
    private val evolveSpiritUseCase: EvolveSpiritUseCase,
    private val analyticsTracker: AnalyticsTracker
) {

    /**
     * Scans a plant and updates the Spirit's state.
     * @param imageBase64 The base64-encoded image of the plant.
     * @param imageUri The URI of the image.
     * @return A Result containing the ScanResult.
     */
    suspend operator fun invoke(imageBase64: String, imageUri: String): Result<ScanResult> {
        val scanResult = plantRepository.scanPlant(imageBase64, imageUri)
        if (scanResult is Result.Success) {
            analyticsTracker.trackEvent(
                AnalyticsTracker.PLANT_SCANNED,
                mapOf("species" to scanResult.data.plant.scientificName)
            )
            if (scanResult.data.isNewDiscovery) {
                analyticsTracker.trackEvent(
                    AnalyticsTracker.DISCOVERY,
                    mapOf("species" to scanResult.data.plant.scientificName)
                )
            }
            val spirit = spiritRepository.observeSpirit().first()
            if (spirit != null) {
                val feedResult = feedSpiritUseCase(spirit, scanResult.data.plant, scanResult.data.isNewDiscovery)
                if (feedResult is Result.Success) {
                    val evolveResult = evolveSpiritUseCase(spirit)
                    // TODO: Handle evolution
                } else if (feedResult is Result.Error) {
                    Timber.e(feedResult.exception, "Error feeding spirit")
                }
            }
        } else if (scanResult is Result.Error) {
            Timber.e(scanResult.exception, "Error scanning plant")
        }
        return scanResult
    }
}
