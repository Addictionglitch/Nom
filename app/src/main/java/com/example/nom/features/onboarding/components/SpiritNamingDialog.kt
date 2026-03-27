package com.example.nom.features.onboarding.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun SpiritNamingDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    val isNameValid by remember { derivedStateOf { name.isNotBlank() } }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Name your Spirit") },
        text = {
            TextField(
                value = name,
                onValueChange = { if (it.length <= 20) name = it },
                label = { Text("Spirit's name") },
                singleLine = true
            )
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(name) },
                enabled = isNameValid
            ) {
                Text("Let's go!")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
