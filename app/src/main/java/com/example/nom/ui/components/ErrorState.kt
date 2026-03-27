package com.example.nom.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nom.ui.theme.NomRed
import com.example.nom.ui.theme.NomTextSecondary
import com.example.nom.ui.theme.NomTheme

@Composable
fun ErrorState(message: String, onRetry: (() -> Unit)? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Outlined.ErrorOutline, null, tint = NomRed, modifier = Modifier.size(48.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = NomTextSecondary
        )
        if (onRetry != null) {
            Spacer(modifier = Modifier.height(24.dp))
            NomOutlineButton(onClick = onRetry, text = "Try Again")
        }
    }
}

@Preview
@Composable
fun ErrorStatePreview() {
    NomTheme {
        ErrorState(message = "An error occurred", onRetry = {})
    }
}
