package com.example.nom.features.evolution.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nom.core.domain.models.PlantType
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomDarkSurface
import com.example.nom.ui.theme.NomGold
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomRed
import com.example.nom.ui.theme.NomTealSpirit
import com.example.nom.ui.theme.NomTextPrimary

@Composable
fun DietDonutChart(diet: Map<PlantType, Float>) {
    val total = diet.values.sum()
    val proportions = if (total > 0) diet.mapValues { it.value / total } else mapOf(PlantType.UNKNOWN to 1f)
    
    val colors = mapOf(
        PlantType.FLOWER to Color(0xFFF472B6), // pink
        PlantType.TREE to NomGreenAccent,
        PlantType.MUSHROOM to Color(0xFFA78BFA), // light purple
        PlantType.FERN to NomTealSpirit,
        PlantType.SUCCULENT to NomGold,
        PlantType.HERB to Color(0xFFFDE047), // yellow
        PlantType.GRASS to Color(0xFFA3E635), // lime
        PlantType.VINE to Color(0xFF60A5FA), // blue
        PlantType.AQUATIC to Color(0xFF22D3EE), // cyan
        PlantType.UNKNOWN to NomDarkSurface
    )
    
    val dominantType = if (total > 0) diet.maxByOrNull { it.value }?.key else PlantType.UNKNOWN

    Box(
        modifier = Modifier.size(220.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(200.dp)) {
            // Draw background track
            drawCircle(
                color = NomDarkSurface,
                style = Stroke(width = 24.dp.toPx())
            )

            var startAngle = -90f
            proportions.forEach { (type, proportion) ->
                val sweepAngle = proportion * 360f
                // Only draw if there's an actual sweep to prevent weird visual artifacts
                if (sweepAngle > 0) {
                    drawArc(
                        color = colors[type] ?: NomDarkSurface,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle - 2f, // slight gap between segments
                        useCenter = false,
                        style = Stroke(width = 24.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
                startAngle += sweepAngle
            }
        }
        Text(
            text = dominantType?.name?.lowercase()?.replaceFirstChar { it.uppercase() } ?: "Empty",
            style = MaterialTheme.typography.titleMedium,
            color = NomTextPrimary
        )
    }
}
