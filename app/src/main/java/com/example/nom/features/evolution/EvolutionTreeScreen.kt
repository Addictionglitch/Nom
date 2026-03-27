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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nom.ui.components.NomTopBar
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomGreenAccent

@Composable
fun EvolutionTreeScreen(
    viewModel: EvolutionViewModel = hiltViewModel()
) {
    val evolutionStages by viewModel.evolutionStages.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NomDarkBg)
    ) {
        NomTopBar(title = "Evolution")
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(evolutionStages) { stageUi ->
                EvolutionStageNode(stageUi)
            }
        }
    }
}

@Composable
private fun EvolutionStageNode(stageUi: EvolutionStageUi) {
    val highlightColor = if (stageUi.isUnlocked) NomGreenAccent else Color.Gray
    val modifier = if (stageUi.isCurrent) {
        Modifier.border(2.dp, NomGreenAccent, CircleShape)
    } else {
        Modifier
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(highlightColor.copy(alpha = if (stageUi.isUnlocked) 0.3f else 0.1f))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (stageUi.isUnlocked) stageUi.stage.stage.toString() else "?",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = if (stageUi.isUnlocked) stageUi.stage.name else "??????????",
                color = if (stageUi.isUnlocked) Color.White else Color.Gray,
                fontSize = 16.sp
            )
            Text(
                text = "Requires ${stageUi.stage.xpThreshold} XP",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}
