package com.example.nom.features.spirit.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.tooling.preview.Preview
import com.example.nom.core.domain.models.SpiritEmotion
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomTealSpirit

/**
 * A placeholder for the Spirit view.
 *
 * TODO: Replace with Spine 2D animation.
 *
 * @param emotion The current emotion of the Spirit.
 * @param evolutionStage The current evolution stage of the Spirit.
 */
@Composable
fun SpiritView(emotion: SpiritEmotion, evolutionStage: Int) {
    val scale = remember { Animatable(1f) }

    LaunchedEffect(emotion) {
        scale.animateTo(
            targetValue = 0.95f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1500),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        scale(scale.value) {
            val spiritColor = when (emotion) {
                SpiritEmotion.HAPPY -> NomTealSpirit
                SpiritEmotion.CURIOUS -> NomGreenAccent
                SpiritEmotion.SLEEPY -> Color(0xFF6366F1) // indigo
                SpiritEmotion.QUEASY -> Color(0xFFF87171) // soft red
                SpiritEmotion.EXCITED -> Color(0xFFFBBF24) // gold
            }
            drawCircle(
                color = spiritColor,
                radius = size.minDimension / 2.5f
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.8f),
                radius = size.minDimension / 10f,
                center = center + Offset(
                    x = size.minDimension / 8f,
                    y = -size.minDimension / 8f
                )
            )
        }
    }
}

@Preview
@Composable
fun SpiritViewPreview() {
    SpiritView(emotion = SpiritEmotion.HAPPY, evolutionStage = 1)
}
