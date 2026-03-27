package com.example.nom.core.data.remote.plantid

import com.example.nom.core.domain.models.Plant
import com.example.nom.core.domain.models.PlantType
import com.example.nom.core.domain.models.Rarity

object PlantIdMapper {

    fun toDomain(dto: PlantSuggestion, imageUri: String): Plant {
        val commonName = dto.details.commonNames?.firstOrNull() ?: dto.name
        return Plant(
            id = dto.id,
            commonName = commonName,
            scientificName = dto.name,
            type = PlantType.fromTaxonomy(dto.details.taxonomy.plantClass ?: ""),
            rarity = Rarity.fromOccurrence(dto.probability * 100),
            nutritionValue = 10,
            happinessEffect = if (dto.details.edibleParts?.value == true) 0.1f else -0.1f,
            isToxic = dto.details.edibleParts?.value != true,
            imageUri = imageUri,
            firstDiscoveredAt = System.currentTimeMillis()
        )
    }
}
