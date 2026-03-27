package com.example.nom.features.spirit

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountTree
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nom.core.utils.Constants
import com.example.nom.features.spirit.components.EmptyStateView
import com.example.nom.features.spirit.components.NightModeView
import com.example.nom.features.spirit.components.SpiritView
import com.example.nom.features.spirit.components.StatBars
import com.example.nom.ui.components.LoadingState
import com.example.nom.ui.components.NomGlowBackground
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomGlassFill
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomTextPrimary
import com.example.nom.ui.theme.NomTextSecondary
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
            .background(NomDarkBg)
    ) {
        // Layer 1: Glow Background
        NomGlowBackground(modifier = Modifier.align(Alignment.Center).offset(y = (-40).dp))

        // Layer 2: Floating Particles
        FloatingParticles()

        // Layer 3: Content
        when (val state = uiState) {
            is SpiritUiState.Loading -> LoadingState()
            is SpiritUiState.Empty -> EmptyStateView(onScanClicked)
            is SpiritUiState.Active -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Top Section
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 12.dp)
                    ) {
                        Text(
                            text = state.spirit.name,
                            style = MaterialTheme.typography.titleLarge,
                            color = NomTextPrimary
                        )
                        Text(
                            text = "Level ${state.spirit.level} · ${getEvolutionStageName(state.spirit.evolutionStage)}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = NomTextSecondary
                        )
                    }

                    // Center Spirit
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        SpiritView(
                            emotion = state.spirit.currentEmotion,
                            evolutionStage = state.spirit.evolutionStage
                        )
                    }

                    // Bottom Section
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        StatBars(spirit = state.spirit)

                        Spacer(modifier = Modifier.height(24.dp))

                        // Quick action row
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            GlassCircleButton(
                                icon = Icons.Outlined.AccountTree,
                                onClick = onEvolutionClicked
                            )
                            GlassCircleButton(
                                icon = Icons.Outlined.Eco,
                                onClick = { /* To Journal - stub, navigation handled largely in BottomNavBar */ }
                            )
                            GlassCircleButton(
                                icon = Icons.Outlined.BarChart,
                                onClick = { /* To Status - stub */ }
                            )
                        }
                    }
                }
            }
            is SpiritUiState.NightMode -> NightModeView(spirit = state.spirit)
        }
    }
}

@Composable
fun GlassCircleButton(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(56.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = NomGlassFill,
            contentColor = NomGreenAccent
        )
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(28.dp))
    }
}

private fun getEvolutionStageName(stageIndex: Int): String {
    return Constants.EVOLUTION_STAGES.getOrNull(stageIndex)?.name ?: "Unknown Stage"
}

data class Particle(val x: Float, val y: Float, val radius: Float, val alpha: Float, val speed: Float)

@Composable
fun FloatingParticles() {
    val particles = remember {
        List(30) {
            Particle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                radius = Random.nextFloat() * 3f + 1f,
                alpha = Random.nextFloat() * 0.4f + 0.1f,
                speed = Random.nextFloat() * 2000f + 3000f
            )
        }
    }
    val infiniteTransition = rememberInfiniteTransition(label = "particles")
    val drift by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(5000, easing = LinearEasing), RepeatMode.Restart),
        label = "drift"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        particles.forEach { p ->
            val y = ((p.y + drift * (p.speed / 5000f)) % 1.1f) * size.height
            drawCircle(
                color = NomGreenAccent.copy(alpha = p.alpha),
                radius = p.radius.dp.toPx(),
                center = Offset(p.x * size.width, y)
            )
        }
    }
}
