package com.example.nom.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val NomTypography = Typography(
    displayMedium = TextStyle(
        fontSize = 28.sp, 
        fontWeight = FontWeight.SemiBold, 
        color = NomTextPrimary, 
        letterSpacing = (-0.5).sp
    ),
    headlineMedium = TextStyle(
        fontSize = 22.sp, 
        fontWeight = FontWeight.SemiBold, 
        color = NomTextPrimary
    ),
    titleLarge = TextStyle(
        fontSize = 20.sp, 
        fontWeight = FontWeight.Medium, 
        color = NomTextPrimary
    ),
    titleMedium = TextStyle(
        fontSize = 18.sp, 
        fontWeight = FontWeight.Medium, 
        color = NomTextPrimary
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp, 
        fontWeight = FontWeight.Normal, 
        color = NomTextPrimary, 
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp, 
        fontWeight = FontWeight.Normal, 
        color = NomTextSecondary, 
        lineHeight = 20.sp
    ),
    labelLarge = TextStyle(
        fontSize = 14.sp, 
        fontWeight = FontWeight.Medium, 
        color = NomTextPrimary
    ),
    labelMedium = TextStyle(
        fontSize = 12.sp, 
        fontWeight = FontWeight.Medium, 
        color = NomTextSecondary
    ),
    labelSmall = TextStyle(
        fontSize = 11.sp, 
        fontWeight = FontWeight.SemiBold, 
        color = NomTextMuted, 
        letterSpacing = 1.5.sp
    )
)
