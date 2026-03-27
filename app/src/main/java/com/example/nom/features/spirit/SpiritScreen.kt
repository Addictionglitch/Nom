package com.example.nom.features.spirit

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nom.core.utils.Constants
import com.example.nom.features.spirit.components.EmptyStateView
import com.example.nom.features.spirit.components.NightModeView
import com.example.nom.features.spirit.components.SpiritView
import com.example.nom.features.spirit.components.StatBars
import com.example.nom.ui.components.LoadingState
import com.example.nom.ui.components.NomTextButton
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomGreenAccent
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun SpiritScreen(
    viewModel: SpiritViewModel = hiltViewModel(),
    onScanClicked: () -> Unit,
    onEvolutionClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NomDarkBg),
        contentAlignment = Alignment.Center
    ) {
        FloatingParticles()
        when (val state = uiState) {
            is SpiritUiState.Loading -> LoadingState()
            is SpiritUiState.Empty -> EmptyStateView(onScanClicked)
            is SpiritUiState.Active -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 32.dp)
                    ) {
                        NomTextButton(onClick = onEvolutionClicked, text = "Evolution")
                        Text(
                            text = state.spirit.name,
                            color = Color.White,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Level ${state.spirit.level} · ${getEvolutionStageName(state.spirit.evolutionStage)}",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        SpiritView(
                            emotion = state.spirit.currentEmotion,
                            evolutionStage = state.spirit.evolutionStage
                        )
                    }

                    StatBars(spirit = state.spirit)
                }
            }
            is SpiritUiState.NightMode -> NightModeView(spirit = state.spirit)
        }
    }
}

private fun getEvolutionStageName(stageIndex: Int): String {
    return Constants.EVOLUTION_STAGES.getOrNull(stageIndex)?.name ?: "Unknown Stage"
}

@Composable
fun FloatingParticles() {
    var particles by remember { mutableStateOf(emptyList<Offset>()) }
    var particleRadii by remember { mutableStateOf(emptyList<Float>()) }
    var particleAlphas by remember { mutableStateOf(emptyList<Float>()) }

    LaunchedEffect(Unit) {
        while (true) {
            particles = (0..50).map {
                Offset(
                    x = Random.nextFloat(),
                    y = Random.nextFloat()
                )
            }
            particleRadii = (0..50).map { Random.nextFloat() * 5 }
            particleAlphas = (0..50).map { Random.nextFloat() * 0.6f + 0.1f }
            delay(2000) // slow drift — no flicker
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        particles.forEachIndexed { index, particle ->
            drawCircle(
                color = NomGreenAccent.copy(alpha = particleAlphas.getOrElse(index) { 0.3f }),
                radius = particleRadii.getOrElse(index) { 2f },
                center = Offset(
                    x = particle.x * size.width,
                    y = particle.y * size.height
                )
            )
        }
    }
}

@Preview
@Composable
fun SpiritScreenPreview() {
    SpiritScreen(onScanClicked = {}, onEvolutionClicked = {})
}
