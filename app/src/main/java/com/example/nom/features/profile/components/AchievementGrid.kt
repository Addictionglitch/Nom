package com.example.nom.features.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nom.features.profile.Achievement
import com.example.nom.ui.theme.NomGlassBorder
import com.example.nom.ui.theme.NomGlassFill
import com.example.nom.ui.theme.NomGold
import com.example.nom.ui.theme.NomTextPrimary
import com.example.nom.ui.theme.NomTextSecondary

@Composable
fun AchievementGrid(achievements: List<Achievement>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Achievements",
            style = MaterialTheme.typography.titleMedium,
            color = NomTextPrimary,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(achievements) { achievement ->
                AchievementCard(achievement)
            }
        }
    }
}

@Composable
private fun AchievementCard(achievement: Achievement) {
    val bgColor = if (achievement.unlocked) NomGold.copy(alpha = 0.15f) else NomGlassFill
    val borderColor = if (achievement.unlocked) NomGold.copy(alpha = 0.5f) else NomGlassBorder
    val iconColor = if (achievement.unlocked) NomGold else NomTextSecondary.copy(alpha = 0.5f)
    
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.05f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (achievement.unlocked) Icons.Outlined.EmojiEvents else Icons.Outlined.Lock,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Text(
                text = if (achievement.unlocked) achievement.title else "Locked",
                style = MaterialTheme.typography.labelSmall,
                color = if (achievement.unlocked) NomTextPrimary else NomTextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
