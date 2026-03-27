package com.example.nom.features.profile.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.nom.features.profile.Achievement

@Composable
fun AchievementGrid(achievements: List<Achievement>) {
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(achievements) { achievement ->
            if (achievement.unlocked) {
                Text(text = achievement.title)
            } else {
                Icon(Icons.Default.Lock, contentDescription = "Locked")
                Text(text = achievement.title)
            }
        }
    }
}
