package com.example.nom.core.domain.models

/**
 * Spirit emotion states — drives the Spine 2D animation state machine.
 *
 * Design rule: NEVER include sad, neglected, angry, or lonely.
 * Emotional design is curiosity-based. The Spirit is always in a
 * positive or neutral state.
 */
enum class SpiritEmotion {
    /** Default positive state. Triggered when stats are healthy. */
    HAPPY,

    /** Triggered when Spirit hasn't been fed recently. Exploratory, not needy. */
    CURIOUS,

    /** Triggered when energy is low or it's nighttime (10pm–6am). */
    SLEEPY,

    /** Triggered when Spirit eats a toxic plant. Temporary discomfort, not suffering. */
    QUEASY,

    /** Triggered on new discovery, evolution, or achievement unlock. */
    EXCITED;

    companion object {
        /**
         * Determines the appropriate emotion based on current Spirit stats.
         * Priority order: QUEASY (if recently ate toxic) > SLEEPY (low energy) >
         * EXCITED (level up) > CURIOUS (low hunger) > HAPPY (default)
         *
         * @param hunger 0.0–1.0
         * @param happiness 0.0–1.0
         * @param energy 0.0–1.0
         * @param justAteToxic true if the last plant fed was toxic
         * @param justLeveledUp true if Spirit just gained a level this session
         */
        @JvmStatic
        fun determine(
            hunger: Float,
            happiness: Float,
            energy: Float,
            justAteToxic: Boolean = false,
            justLeveledUp: Boolean = false
        ): SpiritEmotion = when {
            justAteToxic -> QUEASY
            energy < 0.2f -> SLEEPY
            justLeveledUp -> EXCITED
            hunger < 0.3f -> CURIOUS
            else -> HAPPY
        }
    }
}
