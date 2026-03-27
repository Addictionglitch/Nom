package com.example.nom.features.journal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nom.core.domain.models.Plant
import com.example.nom.core.domain.repositories.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlantDetailViewModel @Inject constructor(
    private val plantRepository: PlantRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val plantId: String = savedStateHandle.get<String>("plantId")!!

    val plant: StateFlow<Plant?> = plantRepository.getPlantById(plantId)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
}
