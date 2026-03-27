package com.example.nom.features.spirit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nom.core.domain.models.Spirit
import com.example.nom.core.domain.usecases.GetSpiritStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import javax.inject.Inject

/**
 * Sealed class representing the UI state for the Spirit screen.
 */
sealed class SpiritUiState {
    /** The spirit data is still loading. */
    object Loading : SpiritUiState()

    /** No spirit has been created yet. */
    object Empty : SpiritUiState()

    /** The spirit is active and its data is available. */
    data class Active(val spirit: Spirit) : SpiritUiState()

    /** The spirit is in night mode. */
    data class NightMode(val spirit: Spirit) : SpiritUiState()
}

/**
 * ViewModel for the Spirit home screen.
 *
 * @param getSpiritStateUseCase Use case for observing the spirit's state.
 */
@HiltViewModel
class SpiritViewModel @Inject constructor(
    getSpiritStateUseCase: GetSpiritStateUseCase
) : ViewModel() {

    /**
     * The UI state for the Spirit screen.
     */
    val uiState: StateFlow<SpiritUiState> = getSpiritStateUseCase()
        .map { spirit ->
            if (spirit == null) {
                SpiritUiState.Empty
            } else {
                val calendar = Calendar.getInstance()
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                if (spirit.isSleepy || hour < 6 || hour > 22) {
                    SpiritUiState.NightMode(spirit)
                } else {
                    SpiritUiState.Active(spirit)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SpiritUiState.Loading
        )
}
