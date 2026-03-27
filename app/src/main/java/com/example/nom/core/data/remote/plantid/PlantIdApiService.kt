package com.example.nom.core.data.remote.plantid

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Retrofit service interface for the Plant.id API.
 */
interface PlantIdApiService {

    /**
     * Identifies a plant from an image.
     *
     * @param apiKey The Plant.id API key.
     * @param request The request body containing the image data and other parameters.
     * @return The identification response.
     */
    @POST("identification")
    suspend fun identifyPlant(
        @Header("Api-Key") apiKey: String,
        @Body request: IdentificationRequest
    ): PlantIdResponse
}

data class IdentificationRequest(
    val images: List<String>,
    val similar_images: Boolean = true,
    val plant_details: List<String> = listOf("common_names", "taxonomy", "edible_parts")
)
