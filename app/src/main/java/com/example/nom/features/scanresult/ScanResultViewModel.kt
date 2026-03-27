package com.example.nom.features.scanresult

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nom.core.domain.models.ScanResult
import com.example.nom.core.domain.usecases.FeedSpiritUseCase
import com.example.nom.core.domain.usecases.GetScanResultUseCase
import com.example.nom.core.domain.usecases.GetSpiritStateUseCase
import com.example.nom.core.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Sealed class representing the UI state for the Scan Result screen.
 */
sealed class ScanResultUiState {
    /** The scan result is loading. */
    object Loading : ScanResultUiState()
    /** A new plant has been discovered. */
    data class NewDiscovery(val scanResult: ScanResult) : ScanResultUiState()
    /** A toxic plant has been scanned. */
    data class ToxicResult(val scanResult: ScanResult) : ScanResultUiState()
    /** A non-toxic plant has been scanned. */
    data class PositiveResult(val scanResult: ScanResult) : ScanResultUiState()
    /** An error occurred while loading the scan result. */
    data class Error(val message: String) : ScanResultUiState()
}

/**
 * ViewModel for the Scan Result screen.
 *
 * @param savedStateHandle The saved state handle for accessing navigation arguments.
 * @param getScanResultUseCase The use case for getting a scan result.
 * @param getSpiritStateUseCase The use case for getting the spirit's state.
 * @param feedSpiritUseCase The use case for feeding the spirit.
 */
@HiltViewModel
class ScanResultViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getScanResultUseCase: GetScanResultUseCase,
    private val getSpiritStateUseCase: GetSpiritStateUseCase,
    private val feedSpiritUseCase: FeedSpiritUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScanResultUiState>(ScanResultUiState.Loading)
    val uiState: StateFlow<ScanResultUiState> = _uiState

    init {
        viewModelScope.launch {
            val scanId = savedStateHandle.get<Long>("scanId")
            if (scanId != null) {
                when (val result = getScanResultUseCase(scanId)) {
                    is Result.Success -> {
                        val scanResult = result.data
                        _uiState.value = when {
                            scanResult.isNewDiscovery -> ScanResultUiState.NewDiscovery(scanResult)
                            scanResult.isToxicResult -> ScanResultUiState.ToxicResult(scanResult)
                            else -> ScanResultUiState.PositiveResult(scanResult)
                        }
                    }
                    is Result.Error -> {
                        _uiState.value = ScanResultUiState.Error(result.message ?: "An unknown error occurred")
                    }
                }
            } else {
                _uiState.value = ScanResultUiState.Error("Scan ID not found")
            }
        }
    }

    /**
     * Feeds the spirit with the scanned plant.
     */
    fun feedSpirit() {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is ScanResultUiState.PositiveResult || currentState is ScanResultUiState.NewDiscovery || currentState is ScanResultUiState.ToxicResult) {
                val scanResult = (currentState as? ScanResultUiState.PositiveResult)?.scanResult
                    ?: (currentState as? ScanResultUiState.NewDiscovery)?.scanResult
                    ?: (currentState as? ScanResultUiState.ToxicResult)?.scanResult
                if (scanResult != null) {
                    val spirit = getSpiritStateUseCase().first()
                    if (spirit != null) {
                        feedSpiritUseCase(spirit, scanResult.plant, scanResult.isNewDiscovery)
                    }
                }
            }
        }
    }
}
