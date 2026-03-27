package com.example.nom.core.domain.usecases

import com.example.nom.core.domain.models.Spirit
import com.example.nom.core.domain.repositories.SpiritRepository
import com.example.nom.core.utils.Result
import com.example.nom.core.utils.StatCalculator
import javax.inject.Inject

/**
 * Use case for decaying the Spirit's stats over time.
 *
 * @param spiritRepository The repository for managing the Spirit.
 */
class DecaySpiritStatsUseCase @Inject constructor(
    private val spiritRepository: SpiritRepository
) {

    /**
     * Decays the Spirit's stats based on the time elapsed.
     * @param spirit The current Spirit.
     * @param hoursElapsed The number of hours that have passed since the last decay.
     * @return A Result indicating success or failure.
     */
    suspend operator fun invoke(spirit: Spirit, hoursElapsed: Float): Result<Unit> {
        val updatedSpirit = StatCalculator.applyDecay(spirit, hoursElapsed)
        return spiritRepository.updateSpirit(updatedSpirit)
    }
}
