package com.example.nom.features.journal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.nom.core.domain.models.Plant
import com.example.nom.ui.components.NomCard
import com.example.nom.ui.components.RarityBadge
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomTextPrimary
import com.example.nom.ui.theme.NomTextSecondary

@Composable
fun PlantGridCard(plant: Plant, onClick: () -> Unit) {
    NomCard(
        modifier = Modifier
            .padding(8.dp)
            .height(180.dp)
            .clickable(onClick = onClick)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = plant.imageUri,
                contentDescription = plant.commonName,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            
            // Gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, NomDarkBg.copy(alpha = 0.9f)),
                            startY = 100f
                        )
                    )
                    .clip(RoundedCornerShape(16.dp))
            )

            // Badges
            Box(modifier = Modifier.padding(8.dp).align(Alignment.TopEnd)) {
                RarityBadge(rarity = plant.rarity)
            }

            // Labels
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                Text(
                    text = plant.commonName,
                    style = MaterialTheme.typography.titleMedium,
                    color = NomTextPrimary
                )
                Text(
                    text = plant.type.name.lowercase().replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.labelSmall,
                    color = NomTextSecondary
                )
            }
        }
    }
}
