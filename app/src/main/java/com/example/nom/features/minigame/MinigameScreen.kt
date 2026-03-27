package com.example.nom.features.minigame

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MinigameScreen(
    viewModel: MinigameViewModel = hiltViewModel()
) {
    val canPlay by viewModel.canPlayGardenMinigame.collectAsState()

    Column {
        Card {
            Column {
                Text("Garden Tending")
                if (canPlay) {
                    Text("New challenge!")
                }
                Text("+0.1 happiness, +0.1 energy")
                Button(onClick = { /* TODO: Navigate to garden minigame */ }) {
                    Text("Play")
                }
            }
        }
    }
}
