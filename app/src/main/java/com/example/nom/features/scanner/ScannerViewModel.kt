package com.example.nom.features.scanner

import android.graphics.Bitmap
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nom.core.domain.usecases.ScanPlantUseCase
import com.example.nom.core.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

/**
 * Sealed class representing the UI state for the Scanner screen.
 */
sealed class ScannerUiState {
    /** The scanner is ready to capture an image. */
    object Ready : ScannerUiState()
    /** An image is being captured. */
    object Capturing : ScannerUiState()
    /** The captured image is being processed. */
    object Processing : ScannerUiState()
    /** An error occurred during the scanning process. */
    data class Error(val message: String) : ScannerUiState()
}

/**
 * ViewModel for the Scanner screen.
 *
 * @param scanPlantUseCase The use case for scanning a plant.
 */
@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val scanPlantUseCase: ScanPlantUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScannerUiState>(ScannerUiState.Ready)
    val uiState: StateFlow<ScannerUiState> = _uiState

    /**
     * Captures an image, processes it, and sends it to the ScanPlantUseCase.
     *
     * @param bitmap The bitmap of the captured image.
     */
    fun captureImage(bitmap: Bitmap) {
        viewModelScope.launch {
            _uiState.value = ScannerUiState.Capturing
            val base64Image = withContext(Dispatchers.Default) {
                bitmapToBase64(bitmap)
            }
            _uiState.value = ScannerUiState.Processing
            when (val result = scanPlantUseCase(base64Image, "")) { // TODO: Get image URI
                is Result.Success -> {
                    // TODO: Navigate to ScanResult screen
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
