package com.example.nom.features.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nom.features.profile.components.AchievementGrid

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val spirit by viewModel.spirit.collectAsState()
    val achievements by viewModel.achievements.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        spirit?.let {
            Text(text = it.name) // TODO: Make this editable
            Text(text = "Explorer")
        }
        AchievementGrid(achievements = achievements)
    }
}
