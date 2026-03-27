package com.example.nom.features.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nom.core.domain.repositories.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val plantRepository: PlantRepository
) : ViewModel() {

    val plants = plantRepository.observePlants()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // TODO: Filter state and debounced search
}
