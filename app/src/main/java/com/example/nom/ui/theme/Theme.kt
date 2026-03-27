package com.example.nom.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = NomGreenAccent,
    onPrimary = NomDarkBg,
    secondary = NomTealSpirit,
    background = NomDarkBg,
    surface = NomDarkSurface,
    surfaceVariant = NomDarkCard,
    onBackground = NomTextPrimary,
    onSurface = NomTextPrimary,
    onSurfaceVariant = NomTextSecondary,
    error = NomRed,
    tertiary = NomGreenAccent,    // for the scanner FAB
    outline = NomGlassBorder
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
