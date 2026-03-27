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
import androidx.compose.ui.graphics.Color
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
        val cornerSize = 30.dp.toPx()
        val strokeWidth = 4.dp.toPx()

        // Top-left corner
        drawPath(
            color = NomGreenAccent,
            path = androidx.compose.ui.graphics.Path().apply {
                moveTo(0f, cornerSize)
                lineTo(0f, 0f)
                lineTo(cornerSize, 0f)
            },
            style = Stroke(width = strokeWidth)
        )

        // Top-right corner
        drawPath(
            color = NomGreenAccent,
            path = androidx.compose.ui.graphics.Path().apply {
                moveTo(size.width - cornerSize, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width, cornerSize)
            },
            style = Stroke(width = strokeWidth)
        )

        // Bottom-left corner
        drawPath(
            color = NomGreenAccent,
            path = androidx.compose.ui.graphics.Path().apply {
                moveTo(0f, size.height - cornerSize)
                lineTo(0f, size.height)
                lineTo(cornerSize, size.height)
            },
            style = Stroke(width = strokeWidth)
        )

        // Bottom-right corner
        drawPath(
            color = NomGreenAccent,
            path = androidx.compose.ui.graphics.Path().apply {
                moveTo(size.width - cornerSize, size.height)
                lineTo(size.width, size.height)
                lineTo(size.width, size.height - cornerSize)
            },
            style = Stroke(width = strokeWidth)
        )

        // Pulsing scan line
        val y = size.height * scanPosition
        drawLine(
            color = NomGreenAccent,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = strokeWidth
        )
    }
}

@Preview
@Composable
fun ScanOverlayPreview() {
    ScanOverlay()
}
