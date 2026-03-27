package com.example.nom.features.evolution

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nom.features.evolution.components.DietDonutChart
import com.example.nom.ui.components.NomCard
import com.example.nom.ui.theme.NomDarkBg
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit

@Composable
fun SpiritStatusScreen(
    viewModel: EvolutionViewModel = hiltViewModel()
) {
    val spirit by viewModel.spirit.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NomDarkBg)
            .padding(16.dp)
    ) {
        spirit?.let {
            Text(
                text = "Your spirit is feeling ${it.currentEmotion.name.lowercase()}",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            DietDonutChart(diet = it.dietComposition)
            Spacer(modifier = Modifier.height(16.dp))
            StatsGrid(it)
        }
    }
}

@Composable
private fun StatsGrid(spirit: com.example.nom.core.domain.models.Spirit) {
    NomCard {
        Column(modifier = Modifier.padding(16.dp)) {
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
                StatRow("Streak", "🔥 ${spirit.streakDays} day streak")
            }
        }
    }
}

@Composable
private fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.Gray)
        Text(text = value, color = Color.White, fontWeight = FontWeight.Bold)
    }
}
