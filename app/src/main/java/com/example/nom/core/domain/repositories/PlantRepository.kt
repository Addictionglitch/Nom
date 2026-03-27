package com.example.nom.core.domain.repositories

import com.example.nom.core.domain.models.Plant
import com.example.nom.core.domain.models.ScanResult
import com.example.nom.core.utils.Result
import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing plants.
 */
interface PlantRepository {

    /**
     * Observes all plants for changes.
     * @return A Flow of a list of all plants.
     */
    fun observePlants(): Flow<List<Plant>>

    /**
     * Scans a plant.
     * @param imageBase64 The base64-encoded image of the plant.
     * @param imageUri The URI of the image.
     * @return A Result containing the ScanResult.
     */
    suspend fun scanPlant(imageBase64: String, imageUri: String): Result<ScanResult>
}
