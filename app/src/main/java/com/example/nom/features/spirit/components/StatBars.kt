package com.example.nom.features.spirit.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nom.core.domain.models.Spirit
import com.example.nom.ui.components.NomCard
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomTealSpirit

@Composable
fun StatBars(spirit: Spirit) {
    NomCard(modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            StatBar(name = "Hunger", value = spirit.hunger, color = NomGreenAccent)
            Spacer(modifier = Modifier.height(12.dp))
            StatBar(name = "Happiness", value = spirit.happiness, color = NomTealSpirit)
            Spacer(modifier = Modifier.height(12.dp))
            StatBar(name = "Energy", value = spirit.energy, color = Color(0xFF6366F1))
        }
    }
}

@Composable
private fun StatBar(name: String, value: Float, color: Color) {
    val animatedValue by animateFloatAsState(targetValue = value, label = name)
    val percentage = (animatedValue * 100).toInt()

    val barColor = when {
        animatedValue < 0.3f -> Color(0xFFF87171) // soft red
        animatedValue < 0.6f -> Color(0xFFFBBF24) // gold
        else -> color
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                color = color,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "$percentage%",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { animatedValue },
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp)),
            color = barColor,
            trackColor = Color.White.copy(alpha = 0.1f)
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
