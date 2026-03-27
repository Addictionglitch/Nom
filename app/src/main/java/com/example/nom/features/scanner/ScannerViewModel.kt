package com.example.nom.features.scanner

import android.graphics.Bitmap
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nom.core.domain.usecases.ScanPlantUseCase
import com.example.nom.core.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

sealed class ScannerUiState {
    object Ready : ScannerUiState()
    object Capturing : ScannerUiState()
    object Processing : ScannerUiState()
    data class Error(val message: String) : ScannerUiState()
}

sealed class NavigationEvent {
    data class ToScanResult(val scanId: String) : NavigationEvent()
}

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val scanPlantUseCase: ScanPlantUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScannerUiState>(ScannerUiState.Ready)
    val uiState: StateFlow<ScannerUiState> = _uiState

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun captureImage(bitmap: Bitmap, imageUri: String) {
        viewModelScope.launch {
            _uiState.value = ScannerUiState.Capturing
            val base64Image = withContext(Dispatchers.Default) {
                bitmapToBase64(bitmap)
            }
            _uiState.value = ScannerUiState.Processing
            when (val result = scanPlantUseCase(base64Image, imageUri)) {
                is Result.Success -> {
                    _navigationEvent.emit(NavigationEvent.ToScanResult(result.data.id))
                    _uiState.value = ScannerUiState.Ready
                }
                is Result.Error -> {
                    val message = result.exception?.message ?: "An unknown error occurred"
                    _uiState.value = ScannerUiState.Error(message)
                }
            }
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}
