package com.example.nom.core.data.remote.plantid

import com.example.nom.core.domain.models.Plant
import com.example.nom.core.domain.models.PlantType
import com.example.nom.core.domain.models.Rarity

/**
 * Maps Plant.id API DTOs to domain models.
 */
object PlantIdMapper {

    /**
     * Maps a PlantSuggestion DTO to a Plant domain model.
     * @param dto The PlantSuggestion DTO.
     * @param imageUri The URI of the image that was scanned.
     * @return The Plant domain model.
     */
    fun toDomain(dto: PlantSuggestion, imageUri: String): Plant {
        return Plant(
            id = dto.id,
            commonName = dto.details.commonNames?.firstOrNull() ?: dto.name,
            scientificName = dto.name,
            type = PlantType.fromTaxonomy(dto.details.taxonomy.plantClass),
            rarity = Rarity.fromOccurrence(dto.probability * 100),
            nutritionValue = 10, // Placeholder
            happinessEffect = if (dto.details.edible_parts?.value == true) 0.1f else -0.1f,
            isToxic = dto.details.edible_parts?.value != true,
            imageUri = imageUri,
            firstDiscoveredAt = System.currentTimeMillis()
        )
    }
}
