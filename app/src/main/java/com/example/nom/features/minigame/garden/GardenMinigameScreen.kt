package com.example.nom.features.minigame.garden

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nom.ui.components.NomButton
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomGreenAccent

@Composable
fun GardenMinigameScreen(
    navController: NavController,
    viewModel: GardenMinigameViewModel = hiltViewModel()
) {
    val plots by viewModel.plots.collectAsState()
    val allGrown by viewModel.allGrown.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is GardenNavigationEvent.NavigateBack -> navController.popBackStack()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NomDarkBg)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(plots.size) { index ->
                PlotView(
                    state = plots[index],
                    onClick = { viewModel.onPlotTapped(index) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (allGrown) {
            NomButton(onClick = { viewModel.onHarvest() }, text = "Harvest (+0.1 happiness, +0.1 energy!)")
        }
    }
}

@Composable
private fun PlotView(state: PlotState, onClick: () -> Unit) {
    val backgroundColor = when (state) {
        PlotState.Empty -> Color.Transparent
        PlotState.Planted -> NomGreenAccent.copy(alpha = 0.5f)
        PlotState.NeedsWater -> Color(0xFFFBBF24).copy(alpha = 0.5f) // gold
        PlotState.Grown -> NomGreenAccent
    }

    val borderColor = when (state) {
        PlotState.Empty -> Color.Gray
        else -> Color.Transparent
    }

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(enabled = state != PlotState.Grown, onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(2.dp, borderColor)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                PlotState.Empty -> Icon(Icons.Default.Add, contentDescription = "Plant", tint = Color.Gray)
                PlotState.Planted -> Text("🌱", fontSize = 32.sp)
                PlotState.NeedsWater -> Icon(Icons.Default.WaterDrop, contentDescription = "Water", tint = Color.White)
                PlotState.Grown -> Icon(Icons.Default.Check, contentDescription = "Grown", tint = Color.White)
            }
        }
    }
}
