package com.example.nom.core.domain.usecases

import com.example.nom.core.domain.models.Plant
import com.example.nom.core.domain.models.Spirit
import com.example.nom.core.domain.repositories.SpiritRepository
import com.example.nom.core.utils.Result
import com.example.nom.core.utils.StatCalculator
import javax.inject.Inject

/**
 * Use case for feeding the Spirit a plant.
 *
 * @param spiritRepository The repository for managing the Spirit.
 */
class FeedSpiritUseCase @Inject constructor(
    private val spiritRepository: SpiritRepository
) {
    /**
     * Feeds the Spirit a plant.
     * @param spirit The current Spirit.
     * @param plant The plant to feed to the Spirit.
     * @param isNewDiscovery Whether the plant is a new discovery.
     * @return A Result indicating success or failure.
     */
    suspend operator fun invoke(spirit: Spirit, plant: Plant, isNewDiscovery: Boolean): Result<Unit> {
        val updatedSpirit = StatCalculator.applyFeeding(spirit, plant, isNewDiscovery)
        return spiritRepository.updateSpirit(updatedSpirit)
    }
}
