package com.example.nom.core.domain.models

/**
 * Represents one stage in the Spirit's 5–7 stage evolution tree.
 *
 * Evolution is NOT just level-gated — diet composition affects which
 * visual VARIANT appears at each stage. A mushroom-heavy Spirit at
 * Stage 3 looks different from a flower-heavy Spirit at Stage 3.
 * For MVP, diet affects appearance (color/accessories) within each stage,
 * not branching paths. Branching may be revisited post-launch.
 */
data class EvolutionStage(
    /** Stage number, 1-based. Stage 1 is the starting form. */
    val stage: Int,
    /** Display name for this evolution stage (e.g., "Seedling", "Sprout"). */
    val name: String,
    /** Minimum total XP required to reach this stage. */
    val xpThreshold: Int,
    /** Flavor text describing this stage's characteristics. */
    val description: String,
    /** Whether the user's Spirit has reached this stage. */
    val isUnlocked: Boolean = false
) {
    init {
        require(stage in 1..MAX_STAGES) { "Stage must be between 1 and $MAX_STAGES, was $stage" }
        require(xpThreshold >= 0) { "XP threshold must be non-negative, was $xpThreshold" }
    }

    /** True if this is the final evolution form. */
    val isFinalStage: Boolean
        get() = stage == MAX_STAGES

    /** True if this is the initial starting form. */
    val isStartingStage: Boolean
        get() = stage == 1

    companion object {
        /** Default number of evolution stages for MVP. Can expand to 7 post-launch. */
        const val DEFAULT_STAGES = 5
        /** Maximum possible stages (future expansion). */
        const val MAX_STAGES = 7
    }
}
