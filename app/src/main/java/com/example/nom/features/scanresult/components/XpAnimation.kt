package com.example.nom.features.scanresult.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp

@Composable
fun XpAnimation(xpGained: Int) {
    val animatedXp by animateIntAsState(
        targetValue = xpGained,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 200f), label = "xp-animation"
    )
    Text(
        text = "+$animatedXp XP",
        fontSize = 48.sp
    )
}
