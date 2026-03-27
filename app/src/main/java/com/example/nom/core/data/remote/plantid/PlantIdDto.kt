package com.example.nom.core.data.remote.plantid

import com.google.gson.annotations.SerializedName

/**
 * Data class for the main response from the Plant.id identification API.
 */
data class PlantIdResponse(
    val result: IdentificationResult,
    @SerializedName("similar_images")
    val similarImages: List<SimilarImage>?
)

/**
 * Data class for the identification result.
 */
data class IdentificationResult(
    val classification: Classification
)

/**
 * Data class for the classification, containing a list of suggestions.
 */
data class Classification(
    val suggestions: List<PlantSuggestion>
)

/**
 * Data class for a single plant suggestion from the API.
 */
data class PlantSuggestion(
    val id: String,
    val name: String,
    val probability: Float,
    val details: PlantDetails
)

/**
 * Data class for the detailed information about a plant suggestion.
 */
data class PlantDetails(
    @SerializedName("common_names")
    val commonNames: List<String>?,
    val taxonomy: Taxonomy,
    @SerializedName("edible_parts")
    val edibleParts: EdibleParts?
)

/**
 * Data class for the plant's taxonomy.
 */
data class Taxonomy(
    @SerializedName("class")
    val plantClass: String?,
    val family: String?,
    val genus: String?,
    val order: String?,
    val phylum: String?,
    val species: String?
)

/**
 * Data class for the plant's edible parts, used to determine toxicity.
 */
data class EdibleParts(
    val value: Boolean?
)

/**
 * Data class for a similar image from the API.
 */
data class SimilarImage(
    val id: String,
    val url: String,
    @SerializedName("similarity")
    val similarity: Float,
    @SerializedName("url_small")
    val urlSmall: String
)
