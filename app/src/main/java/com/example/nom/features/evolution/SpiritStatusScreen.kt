package com.example.nom.features.evolution

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nom.core.domain.models.Spirit
import com.example.nom.features.evolution.components.DietDonutChart
import com.example.nom.ui.components.NomCard
import com.example.nom.ui.components.NomGlowBackground
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomTextPrimary
import com.example.nom.ui.theme.NomTextSecondary
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit

@Composable
fun SpiritStatusScreen(
    navController: NavController,
    viewModel: EvolutionViewModel = hiltViewModel()
) {
    val spirit by viewModel.spirit.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NomDarkBg)
    ) {
        NomGlowBackground(modifier = Modifier.align(Alignment.TopCenter))
        
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.1f))
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Status",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(40.dp))
            }
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                spirit?.let { s ->
                    Text(
                        text = "Current State",
                        style = MaterialTheme.typography.labelMedium,
                        color = NomTextSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Feeling ${s.currentEmotion.name.lowercase().replaceFirstChar { it.uppercase() }}",
                        style = MaterialTheme.typography.displaySmall,
                        color = NomGreenAccent
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    DietDonutChart(diet = s.dietComposition)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Diet Composition",
                        style = MaterialTheme.typography.labelMedium,
                        color = NomTextSecondary
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    StatsGrid(spirit = s)
                }
            }
        }
    }
}

@Composable
private fun StatsGrid(spirit: Spirit) {
    NomCard {
        Column(modifier = Modifier.padding(20.dp)) {
            StatRow("Total Scans", spirit.totalScans.toString())
            StatRow("Species Discovered", spirit.speciesDiscovered.toString())
            StatRow(
                "Age",
                "${
                    ChronoUnit.DAYS.between(
                        Instant.ofEpochMilli(spirit.createdAt).atZone(ZoneId.systemDefault()).toLocalDate(),
                        Instant.now().atZone(ZoneId.systemDefault()).toLocalDate()
                    )
                } days"
            )
            if (spirit.streakDays > 0) {
                StatRow("Streak", "🔥 ${spirit.streakDays} days")
            }
        }
    }
}

@Composable
private fun StatRow(label: String, value: String) {
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
