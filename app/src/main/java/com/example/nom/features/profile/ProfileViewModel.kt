package com.example.nom.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nom.core.domain.models.Spirit
import com.example.nom.core.domain.repositories.SpiritRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Achievement(
    val title: String,
    val unlocked: Boolean,
    val unlockedDate: String? = null // For MVP, we can leave this null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val spiritRepository: SpiritRepository
) : ViewModel() {

    val spirit: StateFlow<Spirit?> = spiritRepository.observeSpirit()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val achievements: StateFlow<List<Achievement>> = spirit.map { spirit ->
        if (spirit == null) {
            emptyList()
        } else {
            checkAchievements(spirit)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateSpiritName(newName: String) {
        viewModelScope.launch {
            spirit.value?.let { currentSpirit ->
                spiritRepository.updateSpirit(currentSpirit.copy(name = newName))
            }
        }
    }

    private fun checkAchievements(spirit: Spirit): List<Achievement> {
        return listOf(
            Achievement("First Scan", spirit.totalScans >= 1),
            Achievement("10 Species", spirit.speciesDiscovered >= 10),
            Achievement("50 Species", spirit.speciesDiscovered >= 50),
            // TODO: Add logic for rarity
            Achievement("First Rare", false),
            Achievement("First Legendary", false),
            Achievement("7 Day Streak", spirit.streakDays >= 7),
            Achievement("30 Day Streak", spirit.streakDays >= 30),
            Achievement("Stage 2", spirit.evolutionStage >= 2),
            Achievement("Stage 3", spirit.evolutionStage >= 3),
            // TODO: Add logic for diet composition
            Achievement("Mushroom Hunter", false)
        )
    }
}
