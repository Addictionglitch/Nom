package com.example.nom.features.scanresult.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.nom.core.domain.models.Plant
import com.example.nom.core.domain.models.PlantType
import com.example.nom.core.domain.models.Rarity
import com.example.nom.ui.components.NomCard
import com.example.nom.ui.components.RarityBadge
import com.example.nom.ui.theme.NomDarkSurface
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomTextPrimary
import com.example.nom.ui.theme.NomTextSecondary
import com.example.nom.ui.theme.NomTheme

@Composable
fun PlantCard(plant: Plant, confidence: Float, isToxic: Boolean) {
    NomCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            // Removed the red border, relying on ToxicWarning component for toxic state.
    ) {
        Column {
            // Top Image Area (full width)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = plant.imageUri),
                    contentDescription = plant.commonName,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                // Confidence badge overlay
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(NomDarkSurface.copy(alpha = 0.8f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Conf: ${"%.0f".format(confidence * 100)}%",
                        style = MaterialTheme.typography.labelSmall,
                        color = NomGreenAccent
                    )
                }
            }

            // Bottom Details Area
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = plant.commonName,
                            style = MaterialTheme.typography.headlineSmall,
                            color = NomTextPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = plant.scientificName,
                            style = MaterialTheme.typography.bodyMedium,
                            color = NomTextSecondary,
                            fontStyle = FontStyle.Italic
                        )
                    }
                    RarityBadge(rarity = plant.rarity)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Type Icon/Label
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.05f))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = plant.type.name,
                            style = MaterialTheme.typography.labelMedium,
                            color = NomTextSecondary
                        )
                    }
                    
                    if (isToxic) {
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "Toxic",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color(0xFFF87171)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PlantCardPreview() {
    NomTheme {
        PlantCard(
            plant = Plant(
                id = "1",
                commonName = "Monstera Obliqua",
                scientificName = "Monstera",
                type = PlantType.VINE,
                rarity = Rarity.LEGENDARY,
                nutritionValue = 10,
                happinessEffect = 0.1f,
                isToxic = true,
                imageUri = "",
                firstDiscoveredAt = 0
            ),
            confidence = 0.96f,
            isToxic = true
        )
    }
}
