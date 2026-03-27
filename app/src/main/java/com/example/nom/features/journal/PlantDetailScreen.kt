package com.example.nom.features.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.nom.features.scanresult.components.ToxicWarning
import com.example.nom.ui.components.NomCard
import com.example.nom.ui.components.RarityBadge
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomTextPrimary
import com.example.nom.ui.theme.NomTextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PlantDetailScreen(
    navController: NavController,
    viewModel: PlantDetailViewModel
) {
    val plant by viewModel.plant.collectAsState(initial = null)

    plant?.let { p ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(NomDarkBg)
        ) {
            // Image Layer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f)
                    .align(Alignment.TopCenter)
            ) {
                AsyncImage(
                    model = p.imageUri,
                    contentDescription = p.commonName,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                // Gradient to blend with background
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, NomDarkBg),
                                startY = 300f
                            )
                        )
                )

                // Back Button
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .padding(top = 48.dp, start = 16.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }

            // Content Layer
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .fillMaxHeight(0.65f)
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(NomDarkBg)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = p.commonName,
                            style = MaterialTheme.typography.displayMedium,
                            color = NomTextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = p.scientificName,
                            style = MaterialTheme.typography.bodyLarge,
                            color = NomTextSecondary,
                            fontStyle = FontStyle.Italic
                        )
                    }
                    RarityBadge(rarity = p.rarity)
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (p.isToxic) {
                    ToxicWarning(visible = true)
                    Spacer(modifier = Modifier.height(24.dp))
                }

                NomCard {
                    Column(modifier = Modifier.padding(20.dp)) {
                        InfoRow("Category", p.type.name.lowercase().replaceFirstChar { it.uppercase() })
                        InfoRow("Nutritional Value", "${p.nutritionValue} XP")
                        InfoRow("Happiness Impact", "+${(p.happinessEffect * 100).toInt()}%")
                        InfoRow("Times Scanned", p.timesScanned.toString())
                        InfoRow(
                            "Discovered On",
                            SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(p.firstDiscoveredAt))
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}



@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = NomTextSecondary)
        Text(text = value, style = MaterialTheme.typography.bodyLarge, color = NomTextPrimary)
    }
}
