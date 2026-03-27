package com.example.nom.features.scanner

import android.graphics.Bitmap
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nom.features.scanner.components.CameraPreview
import com.example.nom.features.scanner.components.ScanOverlay
import com.example.nom.ui.components.ErrorState
import com.example.nom.ui.components.LoadingState

@Composable
fun ScannerScreen(
    viewModel: ScannerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val haptics = LocalHapticFeedback.current
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(onImageCapture = { imageCapture = it })
        ScanOverlay()

        when (val state = uiState) {
            is ScannerUiState.Ready -> {
                Button(
                    onClick = {
                        imageCapture?.let {
                            it.takePicture(
                                ContextCompat.getMainExecutor(context),
                                object : ImageCapture.OnImageCapturedCallback() {
                                    override fun onCaptureSuccess(image: ImageProxy) {
                                        val bitmap = image.toBitmap()
                                        viewModel.captureImage(bitmap)
                                        image.close()
                                    }

                                    override fun onError(exception: androidx.camera.core.ImageCaptureException) {
                                        // Log error
                                    }
                                }
                            )
                        }
                    },
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    Text("Capture")
                }
            }
            is ScannerUiState.Capturing -> LoadingState()
            is ScannerUiState.Processing -> LoadingState()
            is ScannerUiState.Error -> ErrorState(message = state.message)
        }
    }
}
