package com.example.nom.features.profile

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nom.navigation.NomRoutes
import com.example.nom.ui.components.NomButton
import com.example.nom.ui.components.NomCard
import com.example.nom.ui.components.NomGlowBackground
import com.example.nom.ui.components.NomOutlineButton
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomGlassBorder
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomRed
import com.example.nom.ui.theme.NomTextPrimary
import com.example.nom.ui.theme.NomTextSecondary

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val notificationPrefs by viewModel.notificationPrefs.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    "Delete all data?",
                    style = MaterialTheme.typography.titleLarge,
                    color = NomRed
                )
            },
            text = {
                Text(
                    "This action is irreversible and will delete your spirit and all your progress.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = NomTextSecondary
                )
            },
            containerColor = NomDarkBg,
            titleContentColor = NomRed,
            textContentColor = NomTextSecondary,
            confirmButton = {
                TextButton(onClick = {
                    viewModel.onDeleteAllData()
                    navController.navigate(NomRoutes.Onboarding.route) {
                        popUpTo(NomRoutes.Profile.route) { inclusive = true }
                    }
                }) {
                    Text("Delete", color = NomRed)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel", color = NomTextPrimary)
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NomDarkBg)
    ) {
        NomGlowBackground(modifier = Modifier.align(Alignment.TopCenter))

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.1f))
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(40.dp))
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                NomCard {
                    Column(modifier = Modifier.padding(16.dp)) {
                        SettingSwitch(
                            label = "Notifications",
                            checked = notificationPrefs,
                            onCheckedChange = viewModel::onNotificationToggle
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                NomButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com/privacy"))
                        context.startActivity(intent)
                    },
                    text = "Privacy Policy",
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                NomOutlineButton(
                    onClick = { showDeleteDialog = true },
                    text = "Delete all my data",
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                Text(
                    text = "App Version: 1.0.0",
                    style = MaterialTheme.typography.labelMedium,
                    color = NomTextSecondary,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
private fun SettingSwitch(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = NomTextPrimary
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = NomGreenAccent,
                uncheckedThumbColor = NomTextSecondary,
                uncheckedTrackColor = NomDarkBg,
                uncheckedBorderColor = NomGlassBorder
            )
        )
    }
}
