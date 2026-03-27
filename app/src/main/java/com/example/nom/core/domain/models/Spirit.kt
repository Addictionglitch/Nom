package com.example.nom.core.domain.models

/**
 * The Spirit companion — Nom's central character.
 *
 * A blank white form that evolves based on real-world plant scans.
 * The Spirit feeds on scanned plants, gaining XP and changing form
 * across 5–7 evolution stages. Its [dietComposition] (which plant types
 * make up its diet) affects its visual appearance at each stage —
 * a mushroom-heavy diet produces a different look than a flower-heavy one.
 *
 * Design rules:
 * - Curiosity-based, NEVER guilt-based
 * - Left alone 24h → sleepy/curious, NOT dead/starving
 * - Stats floor at [MIN_STAT_VALUE], never reach zero
 * - The Spirit is the brand's face — treat it with warmth
 */
data class Spirit(
    val id: String,
    /** User-chosen name, set during onboarding. Max 20 characters. */
    val name: String,
    /** Hunger level. 0.0 = very hungry, 1.0 = fully fed. Decays over time. */
    val hunger: Float = 0.5f,
    /** Happiness level. 0.0 = lowest, 1.0 = maximum. Affected by feeding and toxic plants. */
    val happiness: Float = 0.5f,
    /** Energy level. 0.0 = exhausted, 1.0 = fully energized. Decays over time. */
    val energy: Float = 0.5f,
    /** Total accumulated experience points across all feedings. */
    val xp: Int = 0,
    /** Current level, derived from XP thresholds. Starts at 1. */
    val level: Int = 1,
    /** Current evolution stage (1–7). Determined by XP thresholds + level. */
    val evolutionStage: Int = 1,
    /** What percentage of the Spirit's diet is each plant type. Values sum to ~1.0. */
    val dietComposition: Map<PlantType, Float> = emptyMap(),
    /** Epoch millis of last feeding event. */
    val lastFedTimestamp: Long = 0L,
    /** Consecutive days with at least one scan. Resets on missed day (no punishment). */
    val streakDays: Int = 0,
    /** Lifetime total number of plant scans. */
    val totalScans: Int = 0,
    /** Unique species discovered so far. */
    val speciesDiscovered: Int = 0,
    /** Epoch millis when this Spirit was created. */
    val createdAt: Long = System.currentTimeMillis()
) {
    /** Current emotion state derived from stats. */
    val currentEmotion: SpiritEmotion
        get() = SpiritEmotion.determine(hunger, happiness, energy)

    /** True if any stat is below the "needs attention" threshold (0.3). */
    val needsAttention: Boolean
        get() = hunger < NEEDS_ATTENTION_THRESHOLD ||
                happiness < NEEDS_ATTENTION_THRESHOLD ||
                energy < NEEDS_ATTENTION_THRESHOLD

    /** True if energy is low enough to trigger night/sleep mode. */
    val isSleepy: Boolean
        get() = energy < SLEEPY_THRESHOLD

    /** The dominant plant type in this Spirit's diet, or null if no plants fed yet. */
    val dominantDietType: PlantType?
        get() = dietComposition.maxByOrNull { it.value }?.key

    /** Average of all three stats. Used for general "health" display. */
    val overallWellbeing: Float
        get() = (hunger + happiness + energy) / 3f

    /** Hours since last feeding, or null if never fed. */
    fun hoursSinceLastFed(currentTimeMillis: Long = System.currentTimeMillis()): Float? {
        if (lastFedTimestamp == 0L) return null
        return (currentTimeMillis - lastFedTimestamp) / (1000f * 60f * 60f)
    }

    companion object {
        /** Stats never drop below this value — Spirit is never "dead". */
        const val MIN_STAT_VALUE = 0.1f
        /** Stats never exceed this value. */
        const val MAX_STAT_VALUE = 1.0f
        /** Below this threshold, the stat bar turns yellow/warning. */
        const val NEEDS_ATTENTION_THRESHOLD = 0.3f
        /** Below this energy threshold, Spirit enters sleep mode. */
        const val SLEEPY_THRESHOLD = 0.2f
        /** Maximum characters for Spirit name. */
        const val MAX_NAME_LENGTH = 20
    }
}
