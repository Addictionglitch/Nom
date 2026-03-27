package com.example.nom.features.minigame.garden

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nom.analytics.AnalyticsTracker
import com.example.nom.core.data.local.SecurePreferences
import com.example.nom.core.domain.repositories.PlantRepository
import com.example.nom.core.domain.repositories.SpiritRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class PlotState {
    Empty,
    Planted,
    NeedsWater,
    Grown
}

sealed class GardenNavigationEvent {
    object NavigateBack : GardenNavigationEvent()
}

@HiltViewModel
class GardenMinigameViewModel @Inject constructor(
    private val spiritRepository: SpiritRepository,
    private val plantRepository: PlantRepository,
    private val securePreferences: SecurePreferences,
    private val analyticsTracker: AnalyticsTracker
) : ViewModel() {

    private val _plots = MutableStateFlow(List(9) { PlotState.Empty })
    val plots = _plots.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<GardenNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    val allGrown = plots.map {
        it.all { plot -> plot == PlotState.Grown }
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)


    fun onPlotTapped(index: Int) {
        when (_plots.value[index]) {
            PlotState.Empty -> plant(index)
            PlotState.NeedsWater -> water(index)
            else -> {}
        }
    }

    private fun plant(index: Int) {
        viewModelScope.launch {
            analyticsTracker.trackEvent(AnalyticsTracker.MINIGAME_PLAYED)
            val plants = plantRepository.observePlants().first()
            if (plants.isNotEmpty()) {
                val updatedPlots = _plots.value.toMutableList()
                updatedPlots[index] = PlotState.Planted
                _plots.value = updatedPlots

                delay(2000)
                val current = _plots.value.toMutableList()
                if (current[index] == PlotState.Planted) {
                    current[index] = PlotState.NeedsWater
                    _plots.value = current
                }
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
            analyticsTracker.trackEvent(AnalyticsTracker.MINIGAME_COMPLETED)
            spiritRepository.applyGardenRewards()
            securePreferences.lastGardenHarvestTimestamp = System.currentTimeMillis()
            _plots.value = List(9) { PlotState.Empty }
            _navigationEvent.emit(GardenNavigationEvent.NavigateBack)
        }
    }
}
