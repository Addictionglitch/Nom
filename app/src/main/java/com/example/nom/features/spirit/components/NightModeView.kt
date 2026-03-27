package com.example.nom.features.spirit.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nom.core.domain.models.Spirit
import com.example.nom.core.domain.models.SpiritEmotion
import com.example.nom.ui.theme.NomDarkBg
import kotlin.random.Random

@Composable
fun NightModeView(spirit: Spirit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(NomDarkBg, Color(0xFF0F0F2E))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Star particles
        val stars = remember {
            List(50) {
                Offset(Random.nextFloat(), Random.nextFloat()) to (Random.nextFloat() * 0.2f + 0.1f)
            }
        }
        
        Canvas(modifier = Modifier.fillMaxSize()) {
            stars.forEach { (pos, alpha) ->
                drawCircle(
                    color = Color.White.copy(alpha = alpha),
                    radius = 2f,
                    center = Offset(pos.x * size.width, pos.y * size.height)
                )
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            SpiritView(emotion = SpiritEmotion.SLEEPY, evolutionStage = spirit.evolutionStage)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Your Spirit is resting",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF818CF8)
            )
        }
    }
}

@Preview
@Composable
fun NightModeViewPreview() {
    NightModeView(
        spirit = Spirit(
            id = "1",
            name = "Nom",
            evolutionStage = 1
        )
    )
}
