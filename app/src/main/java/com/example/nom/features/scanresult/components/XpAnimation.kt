package com.example.nom.features.scanresult.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.nom.ui.theme.NomGreenAccent

@Composable
fun XpAnimation(xpGained: Int) {
    var targetValue by remember { mutableStateOf(0) }
    
    val currentXp by animateIntAsState(
        targetValue = targetValue,
        animationSpec = tween(
            durationMillis = 1500,
            easing = FastOutSlowInEasing
        ),
        label = "xp-count"
    )

    LaunchedEffect(xpGained) {
        targetValue = xpGained
    }

    Box(contentAlignment = Alignment.Center) {
        Text(
            text = "+$currentXp XP",
            style = MaterialTheme.typography.displaySmall,
            color = NomGreenAccent
        )
    }
}
