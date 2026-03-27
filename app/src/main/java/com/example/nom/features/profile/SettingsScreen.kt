package com.example.nom.features.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Column {
        Row {
            Text("Notifications")
            Switch(checked = false, onCheckedChange = { viewModel.onNotificationToggle(it) })
        }
        Row {
            Text("Sound")
            Switch(checked = false, onCheckedChange = {})
        }
        Row {
            Text("Dark Mode")
            Switch(checked = false, onCheckedChange = {}, enabled = false)
        }
        Button(onClick = { /* TODO: Open privacy policy */ }) {
            Text("Privacy Policy")
        }
        Button(onClick = { viewModel.onDeleteAllData() }) {
            Text("Delete all my data")
        }
        Text("App Version: 1.0.0")
    }
}
