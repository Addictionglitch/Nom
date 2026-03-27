package com.example.nom.features.evolution

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Lock
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
import com.example.nom.features.evolution.components.XpProgressBar
import com.example.nom.ui.components.NomCard
import com.example.nom.ui.components.NomGlowBackground
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomGlassBorder
import com.example.nom.ui.theme.NomGlassFill
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomTealSpirit
import com.example.nom.ui.theme.NomTextPrimary
import com.example.nom.ui.theme.NomTextSecondary

@Composable
fun EvolutionTreeScreen(
    navController: NavController,
    viewModel: EvolutionViewModel = hiltViewModel()
) {
    val evolutionStages by viewModel.evolutionStages.collectAsState()
    val spirit by viewModel.spirit.collectAsState()
    
    val currentXp = spirit?.xp ?: 0
    val currentStage = evolutionStages.find { it.isCurrent }
    val nextStage = evolutionStages.find { !it.isUnlocked }
    
    val progress = if (currentStage != null && nextStage != null) {
        val xpIntoStage = currentXp - currentStage.stage.xpThreshold
        val requiredXpForNext = nextStage.stage.xpThreshold - currentStage.stage.xpThreshold
        (xpIntoStage.toFloat() / requiredXpForNext.toFloat()).coerceIn(0f, 1f)
    } else if (nextStage == null) {
        1f
    } else {
        0f
    }
    
    val nextThreshold = nextStage?.stage?.xpThreshold ?: currentXp

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
                    text = "Evolution Tree",
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
                    .padding(24.dp)
            ) {
                // Progress Bar
                NomCard {
                    Column(modifier = Modifier.padding(20.dp)) {
                        XpProgressBar(
                            currentXp = currentXp,
                            nextThreshold = nextThreshold,
                            progress = progress
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(evolutionStages) { stageUi ->
                        EvolutionStageNode(stageUi)
                    }
                }
            }
        }
    }
}

@Composable
private fun EvolutionStageNode(stageUi: EvolutionStageUi) {
    if (stageUi.isUnlocked) {
        // Unlocked Node (Glassmorphism)
        NomCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(if (stageUi.isCurrent) NomTealSpirit.copy(alpha=0.2f) else NomGlassFill)
                        .border(1.dp, if (stageUi.isCurrent) NomTealSpirit else NomGlassBorder, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "V${stageUi.stage.stage}",
                        style = MaterialTheme.typography.titleMedium,
                        color = if (stageUi.isCurrent) NomTealSpirit else NomGreenAccent
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stageUi.stage.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = NomTextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (stageUi.isCurrent) "Current Stage" else "Unlocked at ${stageUi.stage.xpThreshold} XP",
                        style = MaterialTheme.typography.bodyMedium,
                        color = NomTextSecondary
                    )
                }
            }
        }
    } else {
        // Locked Node
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .border(1.dp, NomTextSecondary.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.05f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.Lock,
                    contentDescription = "Locked",
                    tint = NomTextSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Unknown Stage",
                    style = MaterialTheme.typography.titleMedium,
                    color = NomTextSecondary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Requires ${stageUi.stage.xpThreshold} XP",
                    style = MaterialTheme.typography.bodyMedium,
                    color = NomTextSecondary.copy(alpha = 0.7f)
                )
            }
        }
    }
}
