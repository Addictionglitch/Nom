package com.example.nom.features.spirit

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nom.features.spirit.components.EmptyStateView
import com.example.nom.features.spirit.components.NightModeView
import com.example.nom.features.spirit.components.SpiritView
import com.example.nom.features.spirit.components.StatBars
import com.example.nom.ui.components.LoadingState
import com.example.nom.ui.theme.NomGreenAccent
import kotlin.random.Random

@Composable
fun SpiritScreen(
    viewModel: SpiritViewModel = hiltViewModel(),
    onScanClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onScanClicked,
                containerColor = NomGreenAccent
            ) {
                Text("Scan")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            FloatingParticles()
            when (val state = uiState) {
                is SpiritUiState.Loading -> LoadingState()
                is SpiritUiState.Empty -> EmptyStateView(onScanClicked)
                is SpiritUiState.Active -> {
                    SpiritView(
                        emotion = state.spirit.currentEmotion,
                        evolutionStage = state.spirit.evolutionStage
                    )
                    StatBars(spirit = state.spirit)
                }

                is SpiritUiState.NightMode -> NightModeView(spirit = state.spirit)
            }
        }
    }
}

@Composable
fun FloatingParticles() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        for (i in 0..50) {
            drawCircle(
                color = Color.White.copy(alpha = Random.nextFloat()),
                radius = Random.nextFloat() * 10,
                center = Offset(
                    x = Random.nextFloat() * size.width,
                    y = Random.nextFloat() * size.height
                )
            )
        }
    }
}

@Preview
@Composable
fun SpiritScreenPreview() {
    SpiritScreen {}
}
