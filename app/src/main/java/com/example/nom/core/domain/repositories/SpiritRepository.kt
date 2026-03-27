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
     * @param spirit The Spirit to create.
     * @return A Result indicating success or failure.
     */
    suspend fun createSpirit(spirit: Spirit): Result<Unit>

    /**
     * Updates the Spirit.
     * @param spirit The Spirit to update.
     * @return A Result indicating success or failure.
     */
    suspend fun updateSpirit(spirit: Spirit): Result<Unit>
}
