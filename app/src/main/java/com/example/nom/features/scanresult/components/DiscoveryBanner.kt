package com.example.nom.features.scanresult.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DiscoveryBanner(visible: Boolean) {
    AnimatedVisibility(visible = visible) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFBBF24))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "New species discovered!",
                color = Color.Black,
                fontSize = 20.sp
            )
        }
    }
}
