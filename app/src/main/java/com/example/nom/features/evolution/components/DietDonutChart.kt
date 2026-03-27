package com.example.nom.features.evolution.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nom.core.domain.models.PlantType

@Composable
fun DietDonutChart(diet: Map<PlantType, Float>) {
    val total = diet.values.sum()
    val proportions = diet.mapValues { it.value / total }
    val colors = mapOf(
        PlantType.FLOWER to Color(0xFFEC4899), // pink
        PlantType.TREE to Color(0xFF16A34A), // green
        PlantType.MUSHROOM to Color(0xFF9333EA), // purple
        PlantType.FERN to Color(0xFF14B8A6), // teal
        PlantType.SUCCULENT to Color(0xFFF97316), // coral
        PlantType.HERB to Color(0xFFF59E0B), // amber
        PlantType.GRASS to Color(0xFF84CC16), // lime
        PlantType.VINE to Color(0xFF3B82F6), // blue
        PlantType.AQUATIC to Color(0xFF06B6D4), // cyan
        PlantType.UNKNOWN to Color.Gray
    )
    val dominantType = diet.maxByOrNull { it.value }?.key

    Box(
        modifier = Modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(200.dp)) {
            var startAngle = -90f
            proportions.forEach { (type, proportion) ->
                val sweepAngle = proportion * 360f
                drawArc(
                    color = colors[type] ?: Color.Gray,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)
                )
                startAngle += sweepAngle
            }
        }
        Text(
            text = dominantType?.name?.lowercase()?.replaceFirstChar { it.uppercase() } ?: "No data",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}
