package com.example.nom.features.journal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nom.core.domain.models.Rarity

@Composable
fun RarityBadge(rarity: Rarity, modifier: Modifier = Modifier) {
    val color = when (rarity) {
        Rarity.COMMON -> Color.Gray
        Rarity.UNCOMMON -> Color.Green
        Rarity.RARE -> Color.Blue
        Rarity.LEGENDARY -> Color(0xFFFFD700) // Gold
    }
    Box(
        modifier = modifier
            .padding(8.dp)
            .background(color, RoundedCornerShape(4.dp))
    ) {
        Text(
            text = rarity.name,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
        )
    }
}
