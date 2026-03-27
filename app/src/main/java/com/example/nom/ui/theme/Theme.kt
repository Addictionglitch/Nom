package com.example.nom.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = NomGreenAccent,
    secondary = NomTealSpirit,
    background = NomDarkBg
)

@Composable
fun NomTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = NomTypography,
        shapes = NomShapes,
        content = content
    )
}
