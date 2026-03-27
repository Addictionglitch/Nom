package com.example.nom.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nom.core.domain.models.Rarity

@Composable
fun RarityBadge(rarity: Rarity) {
    val color = when (rarity) {
        Rarity.COMMON -> Color.Gray
        Rarity.UNCOMMON -> Color.Green
        Rarity.RARE -> Color.Blue
        Rarity.LEGENDARY -> Color.Magenta
    }
    Box(
        modifier = Modifier
            .background(color, RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = rarity.name,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun RarityBadgePreview() {
    RarityBadge(Rarity.RARE)
}
