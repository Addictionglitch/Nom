package com.example.nom.core.domain.usecases

import com.example.nom.core.domain.models.Spirit
import com.example.nom.core.domain.repositories.SpiritRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for observing the current state of the Spirit.
 *
 * This provides a continuous stream of Spirit updates from the local database,
 * allowing the UI to reactively display changes to stats, emotion, etc.
 *
 * @param spiritRepository The repository responsible for Spirit data.
 */
class GetSpiritStateUseCase @Inject constructor(
    private val spiritRepository: SpiritRepository
) {
    /**
     * Invokes the use case to get the Spirit's state as a Flow.
     * @return A Flow that emits the current [Spirit] or null if none exists.
     */
    operator fun invoke(): Flow<Spirit?> = spiritRepository.observeSpirit()
}
