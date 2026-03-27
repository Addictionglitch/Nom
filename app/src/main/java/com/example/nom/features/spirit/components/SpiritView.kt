package com.example.nom.features.spirit.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nom.core.domain.models.SpiritEmotion

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
    // Placeholder for Spine 2D animation
    Canvas(modifier = Modifier.size(200.dp)) {
        drawCircle(
            color = when (emotion) {
                SpiritEmotion.HAPPY -> Color.Green
                SpiritEmotion.CURIOUS -> Color.Yellow
                SpiritEmotion.SLEEPY -> Color.Blue
                SpiritEmotion.QUEASY -> Color.Red
                SpiritEmotion.EXCITED -> Color.Magenta
            }
        )
    }
}

@Preview
@Composable
fun SpiritViewPreview() {
    SpiritView(emotion = SpiritEmotion.HAPPY, evolutionStage = 1)
}
