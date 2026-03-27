package com.example.nom.features.scanner.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nom.ui.theme.NomGreenAccent

@Composable
fun ScanOverlay() {
    val infiniteTransition = rememberInfiniteTransition(label = "scan-animation")
    val scanPosition by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "scan-position"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val cornerSize = 40.dp.toPx()
        val strokeWidth = 3.dp.toPx()

        // Vignette
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)),
                radius = size.width
                // Default center is center of canvas
            )
        )

        val drawCorner = { path: Path ->
            drawPath(
                color = NomGreenAccent,
                path = path,
                style = Stroke(width = strokeWidth)
            )
        }

        // Top-left
        drawCorner(Path().apply {
            moveTo(0f, cornerSize)
            lineTo(0f, 0f)
            lineTo(cornerSize, 0f)
        })

        // Top-right
        drawCorner(Path().apply {
            moveTo(size.width - cornerSize, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, cornerSize)
        })

        // Bottom-left
        drawCorner(Path().apply {
            moveTo(0f, size.height - cornerSize)
            lineTo(0f, size.height)
            lineTo(cornerSize, size.height)
        })

        // Bottom-right
        drawCorner(Path().apply {
            moveTo(size.width - cornerSize, size.height)
            lineTo(size.width, size.height)
            lineTo(size.width, size.height - cornerSize)
        })

        // Soft pulsing scan line
        val lineY = size.height * scanPosition
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(Color.Transparent, NomGreenAccent, Color.Transparent),
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f)
            ),
            topLeft = Offset(x = 0f, y = lineY),
            size = Size(width = size.width, height = 2.dp.toPx())
        )
    }
}

@Preview
@Composable
fun ScanOverlayPreview() {
    ScanOverlay()
}
