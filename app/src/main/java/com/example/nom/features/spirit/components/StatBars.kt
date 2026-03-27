package com.example.nom.features.spirit.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nom.core.domain.models.Spirit

@Composable
fun StatBars(spirit: Spirit) {
    Column(modifier = Modifier.padding(16.dp)) {
        StatBar(name = "Hunger", value = spirit.hunger, color = Color.Green)
        Spacer(modifier = Modifier.height(8.dp))
        StatBar(name = "Happiness", value = spirit.happiness, color = Color(0xFFFF69B4))
        Spacer(modifier = Modifier.height(8.dp))
        StatBar(name = "Energy", value = spirit.energy, color = Color.Blue)
    }
}

@Composable
private fun StatBar(name: String, value: Float, color: Color) {
    val animatedValue by animateFloatAsState(targetValue = value, label = name)
    val barColor = when {
        animatedValue < 0.3f -> Color.Red
        animatedValue < 0.6f -> Color.Yellow
        else -> color
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(name, modifier = Modifier.width(80.dp))
        LinearProgressIndicator(
            progress = { animatedValue },
            modifier = Modifier
                .weight(1f)
                .height(20.dp)
                .clip(RoundedCornerShape(10.dp)),
            color = barColor
        )
    }
}

@Preview
@Composable
fun StatBarsPreview() {
    StatBars(
        spirit = Spirit(
            id = "1",
            name = "Nom",
            hunger = 0.7f,
            happiness = 0.5f,
            energy = 0.3f
        )
    )
}
