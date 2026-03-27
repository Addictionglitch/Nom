package com.example.nom.features.minigame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nom.navigation.NomRoutes
import com.example.nom.ui.components.NomButton
import com.example.nom.ui.components.NomCard
import com.example.nom.ui.theme.NomDarkBg

@Composable
fun MinigameScreen(
    navController: NavController,
    viewModel: MinigameViewModel = hiltViewModel()
) {
    val canPlay by viewModel.canPlayGardenMinigame.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NomDarkBg)
            .padding(16.dp)
    ) {
        NomCard {
            Column(modifier = Modifier.padding(16.dp)) {
                if (canPlay) {
                    Text("Daily challenge!", color = Color.Green)
                }
                Text("Garden Tending", color = Color.White)
                Text("+0.1 happiness, +0.1 energy", color = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))
                NomButton(
                    onClick = { navController.navigate(NomRoutes.GardenMinigame.route) },
                    text = if (canPlay) "Play" else "Come back tomorrow!",
                    enabled = canPlay
                )
            }
        }
    }
}
