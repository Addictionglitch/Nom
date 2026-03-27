package com.example.nom.features.scanresult.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import com.example.nom.ui.theme.NomGreenAccent
import kotlinx.coroutines.delay

@Composable
fun XpAnimation(xpGained: Int) {
    var animatedTarget by remember { mutableIntStateOf(0) }
    val animatedXp by animateIntAsState(
        targetValue = animatedTarget,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 200f), label = "xp-animation"
    )

    LaunchedEffect(xpGained) {
        delay(300) // brief pause before counting up
        animatedTarget = xpGained
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "+$animatedXp",
            fontSize = 48.sp,
            color = NomGreenAccent
        )
        Text(
            text = "XP",
            fontSize = 24.sp,
            color = NomGreenAccent.copy(alpha = 0.8f)
        )
    }
}
