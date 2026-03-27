package com.example.nom.features.evolution

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nom.core.domain.models.EvolutionStage
import com.example.nom.core.domain.models.Spirit
import com.example.nom.core.domain.usecases.GetSpiritStateUseCase
import com.example.nom.core.utils.Constants
import com.example.nom.core.utils.StatCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class EvolutionStageUi(
    val stage: EvolutionStage,
    val isUnlocked: Boolean,
    val isCurrent: Boolean
)

@HiltViewModel
class EvolutionViewModel @Inject constructor(
    getSpiritStateUseCase: GetSpiritStateUseCase
) : ViewModel() {

    val spirit: StateFlow<Spirit?> = getSpiritStateUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    val evolutionStages: StateFlow<List<EvolutionStageUi>> = spirit.map { spirit ->
        val currentStageIndex = spirit?.evolutionStage ?: 0
        Constants.EVOLUTION_STAGES.mapIndexed { index, stage ->
            EvolutionStageUi(
                stage = stage,
                isUnlocked = spirit?.xp ?: 0 >= stage.xpThreshold,
                isCurrent = index == currentStageIndex
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val xpProgress: StateFlow<Float> = spirit.map { spirit ->
        if (spirit != null) {
            StatCalculator.xpProgressToNextEvolution(spirit.xp)
        } else {
            0f
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0f)
}
