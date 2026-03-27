package com.example.nom.core.data.remote.plantid

import com.google.gson.annotations.SerializedName

/**
 * DTO for the main response from the Plant.id identification API.
 */
data class PlantIdResponse(
    val result: IdentificationResult
)

/**
 * DTO for the identification result.
 */
data class IdentificationResult(
    val classification: Classification
)

/**
 * DTO for the classification, containing a list of suggestions.
 */
data class Classification(
    val suggestions: List<PlantSuggestion>
)

/**
 * DTO for a single plant suggestion from the API.
 */
data class PlantSuggestion(
    val id: String,
    val name: String,
    val probability: Float,
    val details: PlantDetails
)

/**
 * DTO for the detailed information about a plant suggestion.
 */
data class PlantDetails(
    @SerializedName("common_names")
    val commonNames: List<String>?,
    val taxonomy: Taxonomy,
    val edible_parts: EdibleParts?
)

/**
 * DTO for the plant's taxonomy.
 */
data class Taxonomy(
    @SerializedName("class")
    val plantClass: String,
    val family: String,
    val genus: String,
    val order: String,
    val phylum: String,
    val species: String
)

/**
 * DTO for the plant's edible parts, used to determine toxicity.
 */
data class EdibleParts(
    val value: Boolean?
)
