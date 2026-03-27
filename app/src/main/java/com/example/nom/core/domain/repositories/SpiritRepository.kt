package com.example.nom.core.domain.repositories

import com.example.nom.core.domain.models.Spirit
import com.example.nom.core.utils.Result
import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing the Spirit.
 */
interface SpiritRepository {

    /**
     * Observes the Spirit for changes.
     * @return A Flow of the Spirit.
     */
    fun observeSpirit(): Flow<Spirit?>

    /**
     * Creates a new Spirit.
     * @param name The name of the Spirit.
     * @return A Result containing the created Spirit.
     */
    suspend fun createSpirit(name: String): Result<Spirit>

    /**
     * Updates the Spirit.
     * @param spirit The Spirit to update.
     * @return A Result indicating success or failure.
     */
    suspend fun updateSpirit(spirit: Spirit): Result<Unit>

    /**
     * Applies the rewards from the garden minigame.
     * @return A Result indicating success or failure.
     */
    suspend fun applyGardenRewards(): Result<Unit>
}
