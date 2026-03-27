package com.example.nom.core.utils

import com.example.nom.core.domain.models.*

/**
 * Pure functions for all Spirit stat calculations.
 *
 * Centralizes game math so it can be unit tested independently.
 * No side effects — takes inputs, returns outputs.
 */
object StatCalculator {

    // ─── LEVEL & EVOLUTION ────────────────────

    /**
     * Calculates the level for a given total XP amount.
     * Uses formula: XP for level N = [Constants.LEVEL_XP_BASE] * N^[Constants.LEVEL_XP_EXPONENT]
     *
     * @param totalXp the Spirit's accumulated XP
     * @return the current level (1-based, capped at [Constants.MAX_LEVEL])
     */
    fun calculateLevel(totalXp: Int): Int {
        var level = 1
        while (level < Constants.MAX_LEVEL) {
            val xpForNext = xpRequiredForLevel(level + 1)
            if (totalXp < xpForNext) break
            level++
        }
        return level
    }

    /**
     * Returns the total XP required to reach a given level.
     *
     * @param level the target level (1-based)
     * @return total XP needed (cumulative, not incremental)
     */
    fun xpRequiredForLevel(level: Int): Int {
        require(level >= 1) { "Level must be >= 1, was $level" }
        if (level == 1) return 0
        return (Constants.LEVEL_XP_BASE * Math.pow(level.toDouble(), Constants.LEVEL_XP_EXPONENT.toDouble())).toInt()
    }

    /**
     * XP progress towards the next level as a fraction (0.0–1.0).
     *
     * @param totalXp current total XP
     * @return progress fraction, or 1.0 if at max level
     */
    fun xpProgressToNextLevel(totalXp: Int): Float {
        val currentLevel = calculateLevel(totalXp)
        if (currentLevel >= Constants.MAX_LEVEL) return 1.0f
        val currentLevelXp = xpRequiredForLevel(currentLevel)
        val nextLevelXp = xpRequiredForLevel(currentLevel + 1)
        val range = nextLevelXp - currentLevelXp
        if (range <= 0) return 1.0f
        return ((totalXp - currentLevelXp).toFloat() / range).coerceIn(0f, 1f)
    }

    /**
     * Determines the evolution stage for a given total XP.
     *
     * @param totalXp the Spirit's accumulated XP
     * @return the highest unlocked [EvolutionStage] number (1-based)
     */
    fun calculateEvolutionStage(totalXp: Int): Int {
        val stages = Constants.EVOLUTION_STAGES
        var result = 1
        for (stage in stages) {
            if (totalXp >= stage.xpThreshold) {
                result = stage.stage
            } else {
                break
            }
        }
        return result
    }

    /**
     * XP progress towards the next evolution stage as a fraction (0.0–1.0).
     *
     * @param totalXp current total XP
     * @return progress fraction, or 1.0 if at final stage
     */
    fun xpProgressToNextEvolution(totalXp: Int): Float {
        val currentStageNum = calculateEvolutionStage(totalXp)
        val stages = Constants.EVOLUTION_STAGES
        val nextStage = stages.firstOrNull { it.stage == currentStageNum + 1 } ?: return 1.0f
        val currentStage = stages.first { it.stage == currentStageNum }
        val range = nextStage.xpThreshold - currentStage.xpThreshold
        if (range <= 0) return 1.0f
        return ((totalXp - currentStage.xpThreshold).toFloat() / range).coerceIn(0f, 1f)
    }

    // ─── FEEDING ──────────────────────────────

    /**
     * Calculates the total XP gained from feeding a plant to the Spirit.
     * Includes base nutrition, rarity multiplier, discovery bonus, and happy plant bonus.
     *
     * @param plant the plant being fed
     * @param isNewDiscovery true if this species hasn't been scanned before
     * @return total XP to award
     */
    fun calculateFeedingXp(plant: Plant, isNewDiscovery: Boolean): Int {
        var xp = plant.effectiveXp

        // Discovery bonus
        if (isNewDiscovery) {
            xp += ScanResult.NEW_DISCOVERY_BONUS_XP
        }

        // Happy plant bonus (plants with strong positive happiness effect)
        if (plant.happinessEffect > 0.1f) {
            xp = (xp * Constants.HAPPY_PLANT_XP_BONUS).toInt()
        }

        return xp
    }

    /**
     * Calculates updated Spirit stats after feeding a plant.
     *
     * @param spirit current Spirit state
     * @param plant the plant being fed
     * @param isNewDiscovery whether this is a new species
     * @param currentTimeMillis current timestamp for lastFedTimestamp
     * @return new Spirit with updated stats (hunger, happiness, energy, XP, diet, etc.)
     */
    fun applyFeeding(
        spirit: Spirit,
        plant: Plant,
        isNewDiscovery: Boolean,
        currentTimeMillis: Long = System.currentTimeMillis()
    ): Spirit {
        val xpGained = calculateFeedingXp(plant, isNewDiscovery)
        val newXp = spirit.xp + xpGained

        val happinessChange = if (plant.isToxic) {
            Constants.FEED_TOXIC_HAPPINESS_PENALTY
        } else {
            Constants.FEED_HAPPINESS_BOOST + plant.happinessEffect
        }

        val newDiet = updateDietComposition(spirit.dietComposition, plant.type)

        return spirit.copy(
            hunger = (spirit.hunger + Constants.FEED_HUNGER_RESTORE).coerceIn(Spirit.MIN_STAT_VALUE, Spirit.MAX_STAT_VALUE),
            happiness = (spirit.happiness + happinessChange).coerceIn(Spirit.MIN_STAT_VALUE, Spirit.MAX_STAT_VALUE),
            energy = (spirit.energy + Constants.FEED_ENERGY_BOOST).coerceIn(Spirit.MIN_STAT_VALUE, Spirit.MAX_STAT_VALUE),
            xp = newXp,
            level = calculateLevel(newXp),
            evolutionStage = calculateEvolutionStage(newXp),
            dietComposition = newDiet,
            lastFedTimestamp = currentTimeMillis,
            totalScans = spirit.totalScans + 1,
            speciesDiscovered = if (isNewDiscovery) spirit.speciesDiscovered + 1 else spirit.speciesDiscovered
        )
    }

    // ─── STAT DECAY ───────────────────────────

    /**
     * Applies time-based stat decay to a Spirit.
     *
     * Decay is GENTLE:
     * - Full stats → needs-attention takes ~35 hours (hunger)
     * - Stats floor at [Spirit.MIN_STAT_VALUE], never reach zero
     * - 24h alone = sleepy/curious, NOT dead/starving
     *
     * @param spirit current Spirit state
     * @param hoursElapsed hours since last decay check
     * @return new Spirit with decayed stats
     */
    fun applyDecay(spirit: Spirit, hoursElapsed: Float): Spirit {
        require(hoursElapsed >= 0f) { "hoursElapsed must be non-negative, was $hoursElapsed" }
        if (hoursElapsed == 0f) return spirit

        return spirit.copy(
            hunger = (spirit.hunger - Constants.HUNGER_DECAY_PER_HOUR * hoursElapsed)
                .coerceIn(Spirit.MIN_STAT_VALUE, Spirit.MAX_STAT_VALUE),
            happiness = (spirit.happiness - Constants.HAPPINESS_DECAY_PER_HOUR * hoursElapsed)
                .coerceIn(Spirit.MIN_STAT_VALUE, Spirit.MAX_STAT_VALUE),
            energy = (spirit.energy - Constants.ENERGY_DECAY_PER_HOUR * hoursElapsed)
                .coerceIn(Spirit.MIN_STAT_VALUE, Spirit.MAX_STAT_VALUE)
        )
    }

    // ─── DIET COMPOSITION ─────────────────────

    /**
     * Recalculates diet composition after feeding a new plant.
     *
     * Uses a weighted running average — new feedings shift the diet
     * gradually rather than abruptly. Values always sum to ~1.0.
     *
     * @param currentDiet current diet map (may be empty for first feeding)
     * @param newType the plant type just fed
     * @param weight how much influence a single feeding has (lower = slower shift)
     * @return updated diet map with values summing to ~1.0
     */
    fun updateDietComposition(
        currentDiet: Map<PlantType, Float>,
        newType: PlantType,
        weight: Float = 0.1f
    ): Map<PlantType, Float> {
        if (currentDiet.isEmpty()) {
            return mapOf(newType to 1.0f)
        }

        val updated = currentDiet.toMutableMap()

        // Reduce all existing entries proportionally
        val reductionFactor = 1f - weight
        for (key in updated.keys) {
            updated[key] = (updated[key] ?: 0f) * reductionFactor
        }

        // Add weight for the new type
        updated[newType] = (updated[newType] ?: 0f) + weight

        // Normalize so values sum to 1.0
        val total = updated.values.sum()
        if (total > 0f) {
            for (key in updated.keys) {
                updated[key] = (updated[key] ?: 0f) / total
            }
        }

        // Remove negligible entries (< 1%)
        return updated.filter { it.value >= 0.01f }
    }

    // ─── SPIRIT REACTION ──────────────────────

    /**
     * Determines what emotion the Spirit shows after eating a plant.
     *
     * @param plant the plant just fed
     * @param isNewDiscovery whether this was a first-time species scan
     * @param previousEvolutionStage the Spirit's evolution stage before feeding
     * @param newEvolutionStage the Spirit's evolution stage after feeding
     * @return the [SpiritEmotion] to display
     */
    fun determineSpiritReaction(
        plant: Plant,
        isNewDiscovery: Boolean,
        previousEvolutionStage: Int,
        newEvolutionStage: Int
    ): SpiritEmotion = when {
        plant.isToxic -> SpiritEmotion.QUEASY
        newEvolutionStage > previousEvolutionStage -> SpiritEmotion.EXCITED
        isNewDiscovery -> SpiritEmotion.EXCITED
        plant.rarity == Rarity.LEGENDARY -> SpiritEmotion.EXCITED
        plant.rarity == Rarity.RARE -> SpiritEmotion.HAPPY
        else -> SpiritEmotion.HAPPY
    }

    // ─── STREAK ───────────────────────────────

    /**
     * Determines if the current day qualifies for streak continuation.
     *
     * Streaks celebrate milestones but NEVER punish breaks.
     * A broken streak just resets to 0 — no negative messaging.
     *
     * @param lastActiveDay epoch millis of the last day the user was active
     * @param currentTimeMillis current time
     * @param currentStreakDays current streak count
     * @return updated streak count (incremented, maintained, or reset to 1)
     */
    fun calculateStreak(
        lastActiveDay: Long,
        currentTimeMillis: Long,
        currentStreakDays: Int
    ): Int {
        if (lastActiveDay == 0L) return 1 // First ever activity

        val lastDayStart = dayStart(lastActiveDay)
        val currentDayStart = dayStart(currentTimeMillis)
        val daysDiff = ((currentDayStart - lastDayStart) / (24 * 60 * 60 * 1000)).toInt()

        return when {
            daysDiff == 0 -> currentStreakDays // Same day, no change
            daysDiff == 1 -> currentStreakDays + 1 // Consecutive day
            else -> 1 // Gap — silently reset (no punishment)
        }
    }

    /**
     * Checks if the given streak count hits a celebration milestone.
     *
     * @param streakDays current streak
     * @return true if this streak count is in [Constants.STREAK_MILESTONES]
     */
    fun isStreakMilestone(streakDays: Int): Boolean =
        streakDays in Constants.STREAK_MILESTONES

    /** Returns the start of the day (midnight) for a given epoch millis (UTC). */
    private fun dayStart(epochMillis: Long): Long {
        val msPerDay = 24 * 60 * 60 * 1000L
        return (epochMillis / msPerDay) * msPerDay
    }
}
