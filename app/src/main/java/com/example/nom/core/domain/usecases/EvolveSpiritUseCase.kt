package com.example.nom.core.domain.usecases

import com.example.nom.core.domain.models.EvolutionStage
import com.example.nom.core.domain.models.Spirit
import com.example.nom.core.utils.Constants
import com.example.nom.core.utils.Result
import javax.inject.Inject

/**
 * Use case for evolving the Spirit.
 */
class EvolveSpiritUseCase @Inject constructor() {

    /**
     * Checks if the Spirit can evolve and returns the new evolution stage.
     * @param spirit The current Spirit.
     * @return A Result containing the new EvolutionStage, or null if the Spirit cannot evolve.
     */
    operator fun invoke(spirit: Spirit): Result<EvolutionStage?> {
        val newStage = Constants.EVOLUTION_STAGES.lastOrNull { spirit.xp >= it.xpThreshold }
        return Result.Success(newStage)
    }
}
