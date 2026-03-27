package com.example.nom.features.scanner

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.outlined.FlashOff
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nom.features.scanner.components.CameraPreview
import com.example.nom.features.scanner.components.ScanOverlay
import com.example.nom.navigation.NomRoutes
import com.example.nom.ui.components.NomOutlineButton
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomDarkSurface
import com.example.nom.ui.theme.NomGlassBorder
import com.example.nom.ui.theme.NomGlassFill
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomRed
import com.example.nom.ui.theme.NomTextPrimary
import com.example.nom.ui.theme.NomTextSecondary

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

    // Determine scale for button animation
    val isCapturing = uiState is ScannerUiState.Capturing
    val buttonScale by animateFloatAsState(
        targetValue = if (isCapturing) 0.9f else 1.0f,
        animationSpec = spring(),
        label = "button-scale"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Camera Layer
        CameraPreview(onImageCapture = { imageCapture = it })
        
        // Overlay Layer
        ScanOverlay()

        // Top Bar Overlay
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack, 
                    contentDescription = "Back", 
                    tint = Color.White
                )
            }
            Text(
                text = "Scan",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(40.dp)) // balance center
        }

        // Bottom Control Area
        val isError = uiState is ScannerUiState.Error
        val bgTint = if (isError) NomRed.copy(alpha = 0.1f) else NomDarkSurface.copy(alpha = 0.85f)
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(bgTint, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .border(1.dp, NomGlassBorder, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .padding(32.dp)
        ) {
            if (isError) {
                val errorState = uiState as ScannerUiState.Error
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = errorState.message,
                        color = NomRed,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.padding(16.dp))
                    NomOutlineButton(
                        text = "Try Again",
                        onClick = { /* Stub - wait for VM implementation */ }
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left button
                    SmallIconButton(Icons.Outlined.PhotoLibrary)

                    // Center Button
                    if (uiState is ScannerUiState.Processing) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = NomGreenAccent, strokeWidth = 3.dp)
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text("Identifying...", color = NomTextSecondary, style = MaterialTheme.typography.bodyMedium)
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .scale(buttonScale)
                                .clip(CircleShape)
                                .background(NomGreenAccent)
                                .clickable {
                                    if (uiState is ScannerUiState.Ready) {
                                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                        imageCapture?.takePicture(
                                            ContextCompat.getMainExecutor(context),
                                            object : ImageCapture.OnImageCapturedCallback() {
                                                override fun onCaptureSuccess(image: ImageProxy) {
                                                    val bitmap = image.toBitmap()
                                                    viewModel.captureImage(bitmap, imageUri = "")
                                                    image.close()
                                                }

                                                override fun onError(exception: androidx.camera.core.ImageCaptureException) {
                                                }
                                            }
                                        )
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Filled.CameraAlt,
                                contentDescription = "Capture",
                                tint = NomDarkBg,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                    // Right button
                    SmallIconButton(Icons.Outlined.FlashOff)
                }
            }
        }
    }
}

@Composable
fun SmallIconButton(icon: ImageVector) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(NomGlassFill)
            .border(1.dp, NomGlassBorder, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, null, tint = NomTextPrimary, modifier = Modifier.size(20.dp))
    }
}
