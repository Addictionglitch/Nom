package com.example.nom.core.domain.services

import com.example.nom.core.domain.models.Plant
import com.example.nom.core.domain.models.SpiritEmotion
import javax.inject.Inject
import kotlin.random.Random

class StatCalculator @Inject constructor() {

    fun calculateFeedingXp(plant: Plant, isNewDiscovery: Boolean): Int {
        // Base XP for feeding
        var xp = 10

        // Bonus for new discovery
        if (isNewDiscovery) {
            xp += 25
        }

        // Add more complex XP calculation logic here if needed
        // For example, based on plant rarity, etc.

        return xp
    }

    fun determineSpiritReaction(): SpiritEmotion {
        // For now, let's return a random emotion
        // More complex logic can be added later based on spirit's current state, preferences, etc.
        return SpiritEmotion.values().random()
    }
}
