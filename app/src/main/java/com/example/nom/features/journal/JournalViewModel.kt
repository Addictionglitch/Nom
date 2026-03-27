package com.example.nom.features.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nom.core.domain.models.Plant
import com.example.nom.core.domain.models.PlantType
import com.example.nom.core.domain.models.Rarity
import com.example.nom.core.domain.repositories.PlantRepository
import com.example.nom.core.utils.PlantFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val plantRepository: PlantRepository
) : ViewModel() {

    private val _filter = MutableStateFlow(PlantFilter())
    private val _searchQuery = MutableStateFlow("")

    val plants = plantRepository.observePlants()
        .combine(_filter) { plants, filter ->
            filter.apply(plants)
        }
        .combine(_searchQuery.debounce(300)) { plants, query ->
            if (query.isBlank()) {
                plants
            } else {
                plants.filter {
                    it.commonName.contains(query, ignoreCase = true) ||
                    it.scientificName.contains(query, ignoreCase = true)
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun updateFilter(plantType: PlantType?, rarity: Rarity?) {
        _filter.value = PlantFilter(type = plantType, rarity = rarity)
    }

    fun updateSearch(query: String) {
        _searchQuery.value = query
    }
}
