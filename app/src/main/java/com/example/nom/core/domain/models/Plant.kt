package com.example.nom.core.domain.models

/**
 * A plant species identified via camera scan.
 *
 * Plants are the core resource in Nom — scanning them feeds the Spirit
 * and drives the entire gameplay loop. Each plant has a type, rarity,
 * and nutritional value that affects how the Spirit evolves.
 *
 * [isToxic] plants trigger the queasy Spirit animation and a warning UI
 * reminding the user that "Spirit eats plants, user should NOT eat them."
 */
data class Plant(
    val id: String,
    val commonName: String,
    val scientificName: String,
    val type: PlantType,
    val rarity: Rarity,
    /** Base XP granted when fed to Spirit (before rarity multiplier). */
    val nutritionValue: Int,
    /** Effect on Spirit happiness when fed. Negative for toxic plants. */
    val happinessEffect: Float,
    /** Triggers queasy animation + warning UI flow. */
    val isToxic: Boolean,
    /** Local URI to the photo taken during scan. */
    val imageUri: String,
    /** GPS location of scan. Null if user denied location permission. */
    val scanLocation: LatLng? = null,
    /** Epoch millis when this species was first scanned by this user. */
    val firstDiscoveredAt: Long,
    /** How many times this exact species has been scanned. */
    val timesScanned: Int = 1
) {
    /** XP granted after applying rarity multiplier. */
    val effectiveXp: Int
        get() = (nutritionValue * rarity.xpMultiplier).toInt()

    /** Display name combining common and scientific names. */
    val fullDisplayName: String
        get() = "$commonName ($scientificName)"

    /** True if this plant has been scanned more than once. */
    val isRepeatScan: Boolean
        get() = timesScanned > 1
}
