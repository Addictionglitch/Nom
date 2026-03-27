package com.example.nom.features.journal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.nom.features.journal.components.JournalFilterBar
import com.example.nom.features.journal.components.PlantGridCard
import com.example.nom.features.journal.components.SpeciesCounter
import com.example.nom.navigation.NomRoutes

@Composable
fun JournalScreen(
    navController: NavController,
    viewModel: JournalViewModel
) {
    val plants by viewModel.plants.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        SpeciesCounter(discovered = plants.size, total = 350)
        JournalFilterBar()
        if (plants.isEmpty()) {
            Text("No plants scanned yet")
        } else {
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(plants) { plant ->
                    PlantGridCard(plant = plant) {
                        navController.navigate(NomRoutes.PlantDetail.createRoute(plant.id))
                    }
                }
            }
        }
    }
}
