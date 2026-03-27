package com.example.nom.features.journal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PlantDetailScreen(
    navController: NavController,
    viewModel: PlantDetailViewModel
) {
    val plant by viewModel.plant.collectAsState(initial = null)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        plant?.let {
            Text(text = it.commonName)
            Text(text = it.scientificName)
            Text(text = "Type: ${it.type.name}")
            Text(text = "Rarity: ${it.rarity.name}")
        }
    }
}
