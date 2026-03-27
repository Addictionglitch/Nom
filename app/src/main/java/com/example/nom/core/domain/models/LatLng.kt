package com.example.nom.core.domain.models

/**
 * Simple latitude/longitude pair for scan location tracking.
 *
 * Kept in the domain layer to avoid Android Location dependency.
 * Location is OPTIONAL — the user may deny location permission,
 * in which case [Plant.scanLocation] will be null.
 */
data class LatLng(
    val latitude: Double,
    val longitude: Double
) {
    init {
        require(latitude in -90.0..90.0) { "Latitude must be between -90 and 90, was $latitude" }
        require(longitude in -180.0..180.0) { "Longitude must be between -180 and 180, was $longitude" }
    }

    /** Returns a formatted string like "56.9496, 24.1052" for display. */
    val displayString: String
        get() = "%.4f, %.4f".format(latitude, longitude)
}
