package com.example.nom.core.domain.models

/**
 * Unlockable achievement displayed on the profile screen.
 *
 * Achievements are always positive/celebratory — they mark milestones
 * the user has reached, never shame the user for things not done.
 */
data class Achievement(
    val id: String,
    /** Display title, e.g., "First Scan". */
    val title: String,
    /** Description of how to unlock, e.g., "Scan your first plant". */
    val description: String,
    /** Resource name for the achievement icon (resolved in UI layer). */
    val iconResName: String,
    /** Whether the user has unlocked this achievement. */
    val isUnlocked: Boolean = false,
    /** Epoch millis when unlocked. Null if still locked. */
    val unlockedAt: Long? = null
) {
    init {
        if (isUnlocked) {
            requireNotNull(unlockedAt) { "Unlocked achievements must have an unlockedAt timestamp" }
        }
    }

    /** True if this achievement is locked and not yet earned. */
    val isLocked: Boolean get() = !isUnlocked
}
