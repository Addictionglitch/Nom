package com.example.nom.features.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.nom.ui.components.NomButton
import com.example.nom.ui.components.NomTextButton
import com.example.nom.ui.components.NomTextField
import com.example.nom.ui.theme.NomDarkCard
import com.example.nom.ui.theme.NomTextMuted
import com.example.nom.ui.theme.NomTextPrimary
import com.example.nom.ui.theme.NomTextSecondary

@Composable
fun SpiritNamingDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    val isNameValid by remember { derivedStateOf { name.isNotBlank() } }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = NomDarkCard,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Name Your Spirit",
                    style = MaterialTheme.typography.headlineMedium,
                    color = NomTextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Choose a name for your companion",
                    style = MaterialTheme.typography.bodyMedium,
                    color = NomTextSecondary
                )
                Spacer(modifier = Modifier.height(24.dp))

                NomTextField(
                    value = name,
                    onValueChange = { if (it.length <= 20) name = it },
                    label = "Spirit Name",
                    leadingIcon = Icons.Outlined.Eco
                )

                Box(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                    Text(
                        text = "${name.length}/20",
                        style = MaterialTheme.typography.labelSmall,
                        color = NomTextMuted,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                NomButton(
                    onClick = { onConfirm(name) },
                    text = "Let's go!",
                    enabled = isNameValid
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    NomTextButton(onClick = onDismiss, text = "Cancel")
                }
            }
        }
    }
}
