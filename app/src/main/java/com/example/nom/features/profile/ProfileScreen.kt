package com.example.nom.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nom.core.utils.Constants
import com.example.nom.features.profile.components.AchievementGrid
import com.example.nom.navigation.NomRoutes
import com.example.nom.ui.components.NomCard
import com.example.nom.ui.theme.NomDarkBg

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val spirit by viewModel.spirit.collectAsState()
    val achievements by viewModel.achievements.collectAsState()
    var isEditingName by remember { mutableStateOf(false) }
    var spiritName by remember { mutableStateOf(spirit?.name ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NomDarkBg)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { navController.navigate(NomRoutes.Settings.route) }) {
                Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
            }
        }
        spirit?.let {
            NomCard {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (isEditingName) {
                        TextField(
                            value = spiritName,
                            onValueChange = { spiritName = it },
                            label = { Text("Spirit Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            IconButton(onClick = {
                                viewModel.updateSpiritName(spiritName)
                                isEditingName = false
                            }) {
                                Text("Save")
                            }
                            IconButton(onClick = { isEditingName = false }) {
                                Text("Cancel")
                            }
                        }
                    } else {
                        Text(
                            text = it.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.clickable { isEditingName = true }
                        )
                    }
                    Text(
                        text = "Level ${it.level} · ${Constants.EVOLUTION_STAGES.getOrNull(it.evolutionStage)?.name}",
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Stat("Total Scans", it.totalScans.toString())
                        Stat("Species Discovered", it.speciesDiscovered.toString())
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        AchievementGrid(achievements = achievements)
    }
}

@Composable
private fun Stat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(text = label, color = Color.Gray)
    }
}
