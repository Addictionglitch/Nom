package com.example.nom.features.journal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun PlantDetailScreen(
    navController: NavController,
    viewModel: PlantDetailViewModel
) {
    val plant by viewModel.plant.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        plant?.let {
            Text(text = it.name)
            Text(text = it.description)
        }
    }
}
