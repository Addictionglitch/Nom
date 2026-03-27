package com.example.nom.features.spirit.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.ElectricBolt
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nom.core.domain.models.Spirit
import com.example.nom.ui.components.NomCard
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomGold
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomRed
import com.example.nom.ui.theme.NomTealSpirit
import com.example.nom.ui.theme.NomTextSecondary
import com.example.nom.ui.theme.NomTheme

@Composable
fun StatBars(spirit: Spirit) {
    NomCard {
        StatBarRow(
            icon = Icons.Outlined.Eco,
            name = "Hunger",
            value = spirit.hunger,
            color = NomGreenAccent
        )
        Spacer(modifier = Modifier.height(16.dp))
        StatBarRow(
            icon = Icons.Outlined.FavoriteBorder,
            name = "Happiness",
            value = spirit.happiness,
            color = NomTealSpirit // or pink
        )
        Spacer(modifier = Modifier.height(16.dp))
        StatBarRow(
            icon = Icons.Outlined.ElectricBolt,
            name = "Energy",
            value = spirit.energy,
            color = Color(0xFF60A5FA) // blue
        )
    }
}

@Composable
private fun StatBarRow(icon: ImageVector, name: String, value: Float, color: Color) {
    val animatedValue by animateFloatAsState(targetValue = value, label = name)
    val percentage = (animatedValue * 100).toInt()

    val barColor = when {
        animatedValue < 0.3f -> NomRed
        animatedValue < 0.6f -> NomGold
        else -> color
    }
    
    // Each bar is a row: Label icon, Stat name, Spacer, Progress bar, Value text
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(0.4f)) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = NomTextSecondary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.labelMedium,
                color = NomTextSecondary
            )
        }
        
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(0.6f)) {
            LinearProgressIndicator(
                progress = { animatedValue },
                modifier = Modifier
                    .weight(1f)
                    .height(10.dp)
                    .clip(RoundedCornerShape(6.dp)),
                color = barColor,
                trackColor = NomDarkBg
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "$percentage%",
                style = MaterialTheme.typography.labelMedium,
                color = NomTextSecondary,
                modifier = Modifier.weight(0.25f)
            )
        }
    }
}

@Preview
@Composable
fun StatBarsPreview() {
    NomTheme {
        StatBars(
            spirit = Spirit(
                id = "1",
                name = "Nom",
                hunger = 0.7f,
                happiness = 0.5f,
                energy = 0.2f
            )
        )
    }
}
