package com.example.nom.features.evolution.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nom.ui.theme.NomGreenAccent

@Composable
fun XpProgressBar(currentXp: Int, nextThreshold: Int, progress: Float) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text("XP: $currentXp / $nextThreshold", color = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth(),
            color = NomGreenAccent,
            trackColor = Color.Gray
        )
    }
}
