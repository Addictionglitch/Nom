package com.example.nom.features.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nom.features.journal.components.EmptyJournalView
import com.example.nom.features.journal.components.JournalFilterBar
import com.example.nom.features.journal.components.PlantGridCard
import com.example.nom.features.journal.components.SpeciesCounter
import com.example.nom.navigation.NomRoutes
import com.example.nom.ui.components.NomGlowBackground
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomTextPrimary

@Composable
fun JournalScreen(
    navController: NavController,
    viewModel: JournalViewModel
) {
    val plants by viewModel.plants.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NomDarkBg)
    ) {
        NomGlowBackground(modifier = Modifier.align(Alignment.TopCenter))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 48.dp)
        ) {
            // Header Area
            Text(
                text = "Journal",
                style = MaterialTheme.typography.displaySmall,
                color = NomTextPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
            SpeciesCounter(discovered = plants.size, total = 350)
            Spacer(modifier = Modifier.height(24.dp))
            JournalFilterBar(
                onFilterChanged = viewModel::updateFilter,
                onSearchQueryChanged = viewModel::updateSearch
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Content Area
            if (plants.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    EmptyJournalView(
                        onScanClicked = { navController.navigate(NomRoutes.Scanner.route) }
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(plants) { plant ->
                        PlantGridCard(plant = plant) {
                            navController.navigate(NomRoutes.PlantDetail.createRoute(plant.id))
                        }
                    }
                }
            }
        }
    }
}
