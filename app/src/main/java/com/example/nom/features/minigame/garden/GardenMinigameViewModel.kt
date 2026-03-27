package com.example.nom.features.minigame.garden

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nom.core.data.local.SecurePreferences
import com.example.nom.core.domain.repositories.PlantRepository
import com.example.nom.core.domain.repositories.SpiritRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class PlotState {
    Empty,
    Planted,
    NeedsWater,
    Grown
}

@HiltViewModel
class GardenMinigameViewModel @Inject constructor(
    private val spiritRepository: SpiritRepository,
    private val plantRepository: PlantRepository,
    private val securePreferences: SecurePreferences
) : ViewModel() {

    private val _plots = MutableStateFlow(List(9) { PlotState.Empty })
    val plots = _plots.asStateFlow()

    fun onPlotTapped(index: Int) {
        when (_plots.value[index]) {
            PlotState.Empty -> plant(index)
            PlotState.NeedsWater -> water(index)
            else -> {}
        }
    }

    private fun plant(index: Int) {
        viewModelScope.launch {
            val plants = plantRepository.observePlants().first()
            if (plants.isNotEmpty()) {
                val updatedPlots = _plots.value.toMutableList()
                updatedPlots[index] = PlotState.Planted
                // In a real game, you'd associate a specific plant with the plot.
                // For this MVP, we'll just change the state.
                _plots.value = updatedPlots
            }
        }
    }

    private fun water(index: Int) {
        val updatedPlots = _plots.value.toMutableList()
        updatedPlots[index] = PlotState.Grown
        _plots.value = updatedPlots
    }

    fun onHarvest() {
        viewModelScope.launch {
            spiritRepository.applyGardenRewards()
            securePreferences.lastGardenHarvestTimestamp = System.currentTimeMillis()
        }
    }
}
