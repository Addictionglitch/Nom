package com.example.nom.features.scanner

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nom.features.scanner.components.CameraPreview
import com.example.nom.features.scanner.components.ScanOverlay
import com.example.nom.navigation.NomRoutes
import com.example.nom.ui.components.ErrorState
import com.example.nom.ui.components.LoadingState
import com.example.nom.ui.theme.NomGreenAccent

@Composable
fun ScannerScreen(
    navController: NavController,
    viewModel: ScannerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val haptics = LocalHapticFeedback.current
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is NavigationEvent.ToScanResult -> {
                    navController.navigate(NomRoutes.ScanResult.createRoute(event.scanId))
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(onImageCapture = { imageCapture = it })
        ScanOverlay()

        when (val state = uiState) {
            is ScannerUiState.Ready -> {
                FloatingActionButton(
                    onClick = {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        imageCapture?.let {
                            it.takePicture(
                                ContextCompat.getMainExecutor(context),
                                object : ImageCapture.OnImageCapturedCallback() {
                                    override fun onCaptureSuccess(image: ImageProxy) {
                                        val bitmap = image.toBitmap()
                                        viewModel.captureImage(bitmap, imageUri = "")  // TODO: Get real URI from ImageCapture output
                                        image.close()
                                    }

                                    override fun onError(exception: androidx.camera.core.ImageCaptureException) {
                                        // Log error
                                    }
                                }
                            )
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 32.dp),
                    containerColor = NomGreenAccent,
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Camera,
                        contentDescription = "Capture",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            is ScannerUiState.Capturing -> LoadingState()
            is ScannerUiState.Processing -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Processing...", color = Color.White)
                }
            }
            is ScannerUiState.Error -> ErrorState(message = state.message)
        }
    }
}
