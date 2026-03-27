package com.example.nom.core.utils

import com.example.nom.core.domain.models.EvolutionStage

/**
 * App-wide constants for game mechanics, decay rates, XP thresholds, and limits.
 *
 * All balancing values live here so they can be tuned in one place.
 * Spirit state decay is GENTLE — the design goal is to make users
 * WANT to come back, never feel GUILTY for leaving.
 */
object Constants {

    // ═══════════════════════════════════════════
    //  XP & LEVELING
    // ═══════════════════════════════════════════

    /** XP required for each level. Level N requires LEVEL_XP_BASE * N^LEVEL_XP_EXPONENT total XP. */
    const val LEVEL_XP_BASE = 50
    const val LEVEL_XP_EXPONENT = 1.4f

    /** Maximum achievable level. */
    const val MAX_LEVEL = 50

    // ═══════════════════════════════════════════
    //  EVOLUTION STAGES
    // ═══════════════════════════════════════════

    /** Default evolution stage definitions for MVP (5 stages). */
    @JvmStatic
    val EVOLUTION_STAGES: List<EvolutionStage> = listOf(
        EvolutionStage(stage = 1, name = "Seedling",    xpThreshold = 0,     description = "A curious new form, just beginning to discover the world."),
        EvolutionStage(stage = 2, name = "Sprout",      xpThreshold = 200,   description = "Growing stronger with each plant it tastes."),
        EvolutionStage(stage = 3, name = "Bloom",       xpThreshold = 800,   description = "Its personality is taking shape, influenced by its diet."),
        EvolutionStage(stage = 4, name = "Flourish",    xpThreshold = 2000,  description = "A vibrant companion, clearly shaped by its Explorer's journeys."),
        EvolutionStage(stage = 5, name = "Evergreen",   xpThreshold = 5000,  description = "A fully evolved Spirit — unique in all the world.")
    )

    /** Total species in the discoverable catalog. Used for "47/350 discovered" counter. */
    const val TOTAL_DISCOVERABLE_SPECIES = 350

    // ═══════════════════════════════════════════
    //  STAT DECAY (per hour)
    // ═══════════════════════════════════════════

    /** Hunger decay per hour. At this rate, full→needs-attention takes ~35 hours. */
    const val HUNGER_DECAY_PER_HOUR = 0.02f

    /** Happiness decay per hour. Slower than hunger — happiness is stickier. */
    const val HAPPINESS_DECAY_PER_HOUR = 0.01f

    /** Energy decay per hour. Medium rate between hunger and happiness. */
    const val ENERGY_DECAY_PER_HOUR = 0.015f

    /** WorkManager check interval in hours. Battery-friendly periodic check. */
    const val SPIRIT_UPDATE_INTERVAL_HOURS = 2L

    // ═══════════════════════════════════════════
    //  FEEDING
    // ═══════════════════════════════════════════

    /** Base hunger restoration when feeding any plant. */
    const val FEED_HUNGER_RESTORE = 0.15f

    /** Base happiness boost from feeding a non-toxic plant. */
    const val FEED_HAPPINESS_BOOST = 0.08f

    /** Happiness penalty from feeding a toxic plant. */
    const val FEED_TOXIC_HAPPINESS_PENALTY = -0.12f

    /** Energy boost from feeding (small — feeding is primarily about hunger/XP). */
    const val FEED_ENERGY_BOOST = 0.05f

    /** Bonus XP multiplier for plants with happiness effect > 0.1. */
    const val HAPPY_PLANT_XP_BONUS = 1.2f

    // ═══════════════════════════════════════════
    //  STREAKS
    // ═══════════════════════════════════════════

    /** Streak milestones that trigger celebration UI (not punishment on miss). */
    @JvmStatic
    val STREAK_MILESTONES = listOf(3, 7, 14, 30, 60, 100, 365)

    // ═══════════════════════════════════════════
    //  NOTIFICATIONS
    // ═══════════════════════════════════════════

    /** Maximum notifications per day. Respectful, not spammy. */
    const val MAX_NOTIFICATIONS_PER_DAY = 2

    /** Stat threshold below which a notification MAY be sent. */
    const val NOTIFICATION_STAT_THRESHOLD = 0.3f

    // ═══════════════════════════════════════════
    //  OFFLINE
    // ═══════════════════════════════════════════

    /** Minimum number of cached plants for offline identification. */
    const val OFFLINE_PLANT_CACHE_MIN_SIZE = 50

    // ═══════════════════════════════════════════
    //  SCAN
    // ═══════════════════════════════════════════

    /** Maximum image size in bytes to send to Plant.id API (~5MB). */
    const val MAX_SCAN_IMAGE_BYTES = 5 * 1024 * 1024

    /** JPEG compression quality for scan images (0–100). */
    const val SCAN_IMAGE_QUALITY = 85

    /** Minimum Plant.id confidence to auto-accept identification. */
    const val MIN_AUTO_ACCEPT_CONFIDENCE = 0.5f
}
