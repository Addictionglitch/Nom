package com.example.nom.features.evolution.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.nom.ui.theme.NomDarkSurface
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomTealSpirit
import com.example.nom.ui.theme.NomTextPrimary
import com.example.nom.ui.theme.NomTextSecondary

@Composable
fun XpProgressBar(currentXp: Int, nextThreshold: Int, progress: Float) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Next Evolution", style = MaterialTheme.typography.labelMedium, color = NomTextSecondary)
            Text("$currentXp / $nextThreshold XP", style = MaterialTheme.typography.labelMedium, color = NomTextPrimary)
        }
        Spacer(modifier = Modifier.height(8.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(14.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(NomDarkSurface)
        ) {
            val validProgress = progress.coerceIn(0f, 1f)
            Box(
                modifier = Modifier
                    .fillMaxWidth(validProgress)
                    .fillMaxHeight()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(NomTealSpirit, NomGreenAccent)
                        )
                    )
            )
        }
    }
}
