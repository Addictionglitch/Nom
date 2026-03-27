package com.example.nom.core.domain.models

/**
 * Result of a single plant scan event.
 *
 * This is the data object that drives the scan result screen —
 * THE core dopamine moment of the app. [isNewDiscovery] triggers
 * a gold/amber achievement banner ("First time scanning this species!").
 * [spiritReaction] determines which Spine 2D animation plays.
 */
data class ScanResult(
    val id: String,
    /** The identified plant. */
    val plant: Plant,
    /** Plant.id API confidence score (0.0–1.0). Displayed as percentage. */
    val confidence: Float,
    /** True if this is the first time the user has scanned this species. */
    val isNewDiscovery: Boolean,
    /** Total XP gained from this scan (includes rarity + discovery bonuses). */
    val xpGained: Int,
    /** Spirit's emotional reaction, drives the post-scan animation. */
    val spiritReaction: SpiritEmotion,
    /** Epoch millis when the scan was performed. */
    val timestamp: Long
) {
    /** Confidence as a display percentage string, e.g., "94.2%". */
    val confidencePercent: String
        get() = "%.1f%%".format(confidence * 100f)

    /** True if confidence is high enough to be considered reliable. */
    val isHighConfidence: Boolean
        get() = confidence >= HIGH_CONFIDENCE_THRESHOLD

    /** True if this scan identified a toxic plant. */
    val isToxicResult: Boolean
        get() = plant.isToxic

    /** Breakdown of XP: base + rarity bonus + discovery bonus. */
    val xpBreakdown: XpBreakdown
        get() {
            val baseXp = plant.nutritionValue
            val rarityBonus = plant.effectiveXp - baseXp
            val discoveryBonus = if (isNewDiscovery) NEW_DISCOVERY_BONUS_XP else 0
            return XpBreakdown(baseXp, rarityBonus, discoveryBonus)
        }

    companion object {
        /** Minimum confidence to consider an identification reliable. */
        const val HIGH_CONFIDENCE_THRESHOLD = 0.7f
        /** Flat XP bonus for scanning a species for the first time. */
        const val NEW_DISCOVERY_BONUS_XP = 50
    }
}

/**
 * Breakdown of XP gained from a single scan, for display on the result screen.
 */
data class XpBreakdown(
    /** Base XP from the plant's nutrition value. */
    val baseXp: Int,
    /** Bonus XP from rarity multiplier. */
    val rarityBonus: Int,
    /** Bonus XP for first discovery (0 if repeat scan). */
    val discoveryBonus: Int
) {
    /** Total XP (sum of all components). */
    val total: Int get() = baseXp + rarityBonus + discoveryBonus
}
