package com.example.nom.features.minigame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nom.navigation.NomRoutes
import com.example.nom.ui.components.NomButton
import com.example.nom.ui.components.NomCard
import com.example.nom.ui.components.NomGlowBackground
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomTextPrimary
import com.example.nom.ui.theme.NomTextSecondary

@Composable
fun MinigameScreen(
    navController: NavController,
    viewModel: MinigameViewModel = hiltViewModel()
) {
    val canPlay by viewModel.canPlayGardenMinigame.collectAsState()

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
            Text(
                text = "Minigames",
                style = MaterialTheme.typography.displaySmall,
                color = NomTextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Play games to boost your Spirit's stats.",
                style = MaterialTheme.typography.bodyLarge,
                color = NomTextSecondary
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            NomCard {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(NomGreenAccent.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Outlined.Eco, contentDescription = null, tint = NomGreenAccent)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Garden Tending", style = MaterialTheme.typography.titleMedium, color = NomTextPrimary)
                            Text("+10% Happiness & Energy", style = MaterialTheme.typography.bodyMedium, color = NomGreenAccent)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    if (canPlay) {
                        NomButton(
                            onClick = { navController.navigate(NomRoutes.GardenMinigame.route) },
                            text = "Play Now",
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.05f))
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Come back tomorrow!",
                                style = MaterialTheme.typography.labelLarge,
                                color = NomTextSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}
