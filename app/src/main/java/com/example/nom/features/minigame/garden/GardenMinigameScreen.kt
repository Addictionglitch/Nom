package com.example.nom.features.minigame.garden

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.EnergySavingsLeaf
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.nom.ui.components.NomButton
import com.example.nom.ui.components.NomGlowBackground
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomDarkSurface
import com.example.nom.ui.theme.NomGlassBorder
import com.example.nom.ui.theme.NomGold
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomTextPrimary

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
                    text = "Garden",
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
                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    text = "Tend to your garden to grow a snack for your Spirit.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = NomTextPrimary,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(48.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(plots.size) { index ->
                        PlotView(
                            state = plots[index],
                            onClick = { viewModel.onPlotTapped(index) }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                if (allGrown) {
                    NomButton(
                        onClick = { viewModel.onHarvest() },
                        text = "Harvest & Feed Spirit",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun PlotView(state: PlotState, onClick: () -> Unit) {
    val bgColor = when (state) {
        PlotState.Empty -> NomDarkSurface
        PlotState.Planted -> NomGreenAccent.copy(alpha = 0.1f)
        PlotState.NeedsWater -> NomGold.copy(alpha = 0.15f)
        PlotState.Grown -> NomGreenAccent.copy(alpha = 0.3f)
    }

    val borderColor = when (state) {
        PlotState.Empty -> NomGlassBorder
        PlotState.Planted -> NomGreenAccent.copy(alpha = 0.5f)
        PlotState.NeedsWater -> NomGold
        PlotState.Grown -> NomGreenAccent
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(20.dp))
            .clickable(enabled = state != PlotState.Grown, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            PlotState.Empty -> Icon(
                Icons.Outlined.EnergySavingsLeaf,
                contentDescription = "Plant",
                tint = NomTextPrimary.copy(alpha = 0.3f),
                modifier = Modifier.size(32.dp)
            )
            PlotState.Planted -> Text("🌱", style = MaterialTheme.typography.displaySmall)
            PlotState.NeedsWater -> Icon(
                Icons.Outlined.WaterDrop,
                contentDescription = "Water",
                tint = NomGold,
                modifier = Modifier.size(32.dp)
            )
            PlotState.Grown -> Icon(
                Icons.Outlined.Check,
                contentDescription = "Grown",
                tint = NomGreenAccent,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
