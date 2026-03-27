package com.example.nom.features.minigame.garden

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun GardenMinigameScreen(
    viewModel: GardenMinigameViewModel = hiltViewModel()
) {
    val plots by viewModel.plots.collectAsState()

    Column {
        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            items(plots.size) { index ->
                Button(onClick = { viewModel.onPlotTapped(index) }) {
                    Text(plots[index].name)
                }
            }
        }
        Button(onClick = { viewModel.onHarvest() }) {
            Text("Harvest")
        }
    }
}
