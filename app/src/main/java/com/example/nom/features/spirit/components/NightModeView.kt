package com.example.nom.features.spirit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.nom.core.domain.models.Spirit
import com.example.nom.core.domain.models.SpiritEmotion

@Composable
fun NightModeView(spirit: Spirit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        SpiritView(emotion = SpiritEmotion.SLEEPY, evolutionStage = spirit.evolutionStage)
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
