package com.example.nom.features.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.nom.features.journal.components.RarityBadge
import com.example.nom.features.scanresult.components.ToxicWarning
import com.example.nom.ui.components.NomCard
import com.example.nom.ui.theme.NomDarkBg
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PlantDetailScreen(
    navController: NavController,
    viewModel: PlantDetailViewModel
) {
    val plant by viewModel.plant.collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NomDarkBg)
    ) {
        plant?.let {
            AsyncImage(
                model = it.imageUri,
                contentDescription = it.commonName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = it.commonName,
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = it.scientificName,
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                    RarityBadge(rarity = it.rarity)
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (it.isToxic) {
                    ToxicWarning(visible = true)
                }
                Spacer(modifier = Modifier.height(16.dp))
                NomCard {
                    Column(modifier = Modifier.padding(16.dp)) {
                        InfoRow("Type", it.type.name.lowercase().replaceFirstChar { it.uppercase() })
                        InfoRow("Nutrition", "${it.nutritionValue} XP")
                        InfoRow("Happiness", "${it.happinessEffect * 100}%")
                        InfoRow("Times Scanned", it.timesScanned.toString())
                        InfoRow(
                            "First Discovered",
                            SimpleDateFormat(
                                "MMM dd, yyyy",
                                Locale.getDefault()
                            ).format(Date(it.firstDiscoveredAt))
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = label, color = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = value, color = Color.White)
    }
}
