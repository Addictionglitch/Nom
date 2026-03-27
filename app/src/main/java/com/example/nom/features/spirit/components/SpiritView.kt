package com.example.nom.features.spirit.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nom.core.domain.models.SpiritEmotion
import com.example.nom.ui.theme.NomGold
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomRed
import com.example.nom.ui.theme.NomTealSpirit
import com.example.nom.ui.theme.NomTheme

@Composable
fun SpiritView(emotion: SpiritEmotion, evolutionStage: Int) {
    val pulse = remember { Animatable(110f) }

    LaunchedEffect(emotion) {
        pulse.animateTo(
            targetValue = 120f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 2000),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    val gradientColors = when (emotion) {
        SpiritEmotion.HAPPY -> listOf(NomTealSpirit, NomGreenAccent)
        SpiritEmotion.CURIOUS -> listOf(NomGreenAccent, NomGold.copy(alpha = 0.7f))
        SpiritEmotion.SLEEPY -> listOf(Color(0xFF6366F1), Color(0xFF818CF8))
        SpiritEmotion.QUEASY -> listOf(NomRed, Color(0xFFFF8A80))
        SpiritEmotion.EXCITED -> listOf(NomGold, NomGreenAccent)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Canvas(modifier = Modifier.size(180.dp).padding(bottom = 16.dp)) {
            val centerOffset = Offset(size.width / 2, size.height / 2)
            
            // Outer glow ring
            drawCircle(
                color = NomTealSpirit.copy(alpha = 0.1f),
                radius = pulse.value.dp.toPx(),
                center = centerOffset
            )

            // Main body
            drawCircle(
                brush = Brush.linearGradient(
                    colors = gradientColors,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, size.height)
                ),
                radius = 60.dp.toPx(),
                center = centerOffset
            )

            // Inner highlight
            drawCircle(
                color = Color.White.copy(alpha = 0.15f),
                radius = 16.dp.toPx(),
                center = centerOffset + Offset(-20.dp.toPx(), -20.dp.toPx())
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = emotion.name,
            style = MaterialTheme.typography.labelMedium,
            color = gradientColors.first()
        )
    }
}

@Preview
@Composable
fun SpiritViewPreview() {
    NomTheme {
        SpiritView(emotion = SpiritEmotion.CURIOUS, evolutionStage = 1)
    }
}
