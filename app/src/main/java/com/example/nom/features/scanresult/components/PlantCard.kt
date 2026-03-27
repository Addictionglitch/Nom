package com.example.nom.features.scanresult.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.nom.core.domain.models.Plant
import com.example.nom.core.domain.models.PlantType
import com.example.nom.core.domain.models.Rarity
import com.example.nom.ui.components.NomCard
import com.example.nom.ui.components.RarityBadge

@Composable
fun PlantCard(plant: Plant, confidence: Float, isToxic: Boolean) {
    NomCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                width = 2.dp,
                color = if (isToxic) Color.Red else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = plant.imageUri),
                contentDescription = plant.commonName,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(plant.commonName, fontSize = 24.sp)
                Text(plant.scientificName, fontStyle = FontStyle.Italic, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                RarityBadge(rarity = plant.rarity)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Confidence: ${"%.1f".format(confidence * 100)}%", fontSize = 14.sp)
            }
        }
    }
}

@Preview
@Composable
fun PlantCardPreview() {
    PlantCard(
        plant = Plant(
            id = "1",
            commonName = "Rose",
            scientificName = "Rosa",
            type = PlantType.FLOWER,
            rarity = Rarity.COMMON,
            nutritionValue = 10,
            happinessEffect = 0.1f,
            isToxic = false,
            imageUri = "",
            firstDiscoveredAt = 0
        ),
        confidence = 0.9f,
        isToxic = false
    )
}
