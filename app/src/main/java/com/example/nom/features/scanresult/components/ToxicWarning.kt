package com.example.nom.features.scanresult.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ToxicWarning(visible: Boolean) {
    AnimatedVisibility(visible = visible) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red.copy(alpha = 0.7f))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Toxic plant!",
                color = Color.White,
                fontSize = 20.sp
            )
            Text(
                text = "Your Spirit can handle it, but this plant is not safe for humans to eat.",
                color = Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
