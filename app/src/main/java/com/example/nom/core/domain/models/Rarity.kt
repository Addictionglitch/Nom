package com.example.nom.core.domain.models

/**
 * Rarity tier for identified plants. Affects:
 * - XP multiplier when fed to Spirit
 * - Badge color in the plant journal
 * - Discovery celebration intensity
 *
 * Distribution targets: ~60% Common, ~25% Uncommon, ~12% Rare, ~3% Legendary
 */
enum class Rarity(
    /** Multiplier applied to base nutrition XP when feeding this plant to Spirit. */
    val xpMultiplier: Float,
    /** Hex color code for the rarity badge in UI. */
    val badgeColor: Long
) {
    COMMON(xpMultiplier = 1.0f, badgeColor = 0xFF9CA3AF),       // Gray
    UNCOMMON(xpMultiplier = 1.5f, badgeColor = 0xFF4ADE80),     // Green (brand accent)
    RARE(xpMultiplier = 2.5f, badgeColor = 0xFF60A5FA),         // Blue
    LEGENDARY(xpMultiplier = 5.0f, badgeColor = 0xFFFBBF24);    // Gold/amber

    companion object {
        /**
         * Determines rarity based on Plant.id occurrence/frequency data.
         * Lower occurrence percentage = higher rarity.
         *
         * @param occurrencePercent estimated global occurrence (0.0–100.0) from API
         */
        @JvmStatic
        fun fromOccurrence(occurrencePercent: Float): Rarity = when {
            occurrencePercent <= 0.5f -> LEGENDARY
            occurrencePercent <= 5.0f -> RARE
            occurrencePercent <= 25.0f -> UNCOMMON
            else -> COMMON
        }
    }
}
