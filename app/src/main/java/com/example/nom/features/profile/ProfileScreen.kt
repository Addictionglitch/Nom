package com.example.nom.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nom.core.utils.Constants
import com.example.nom.features.profile.components.AchievementGrid
import com.example.nom.navigation.NomRoutes
import com.example.nom.ui.components.NomButton
import com.example.nom.ui.components.NomCard
import com.example.nom.ui.components.NomGlowBackground
import com.example.nom.ui.components.NomOutlineButton
import com.example.nom.ui.components.NomTextField
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomGlassFill
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomTextPrimary
import com.example.nom.ui.theme.NomTextSecondary

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val spirit by viewModel.spirit.collectAsState()
    val achievements by viewModel.achievements.collectAsState()
    var isEditingName by remember { mutableStateOf(false) }
    var spiritName by remember { mutableStateOf(spirit?.name ?: "") }

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
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.displaySmall,
                    color = NomTextPrimary
                )
                IconButton(
                    onClick = { navController.navigate(NomRoutes.Settings.route) },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.1f))
                ) {
                    Icon(
                        Icons.Outlined.Settings,
                        contentDescription = "Settings",
                        tint = Color.White
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            spirit?.let { s ->
                NomCard {
                    Column(modifier = Modifier.padding(24.dp)) {
                        if (isEditingName) {
                            NomTextField(
                                value = spiritName,
                                onValueChange = { spiritName = it },
                                label = "Spirit Name"
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                NomOutlineButton(
                                    onClick = { isEditingName = false },
                                    text = "Cancel",
                                    modifier = Modifier.weight(1f)
                                )
                                NomButton(
                                    onClick = {
                                        viewModel.updateSpiritName(spiritName)
                                        isEditingName = false
                                    },
                                    text = "Save",
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = s.name,
                                        style = MaterialTheme.typography.displaySmall,
                                        color = NomGreenAccent
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Level ${s.level} · ${Constants.EVOLUTION_STAGES.getOrNull(s.evolutionStage - 1)?.name ?: "Unknown"}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = NomTextSecondary
                                    )
                                }
                                IconButton(
                                    onClick = { isEditingName = true },
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(NomGlassFill)
                                ) {
                                    Icon(
                                        Icons.Outlined.Edit,
                                        contentDescription = "Edit Name",
                                        tint = NomTextPrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            StatCard("Total Scans", s.totalScans.toString())
                            StatCard("Discovered", s.speciesDiscovered.toString())
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            AchievementGrid(achievements = achievements)
        }
    }
}

@Composable
private fun StatCard(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            color = NomTextPrimary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = NomTextSecondary
        )
    }
}
