package com.example.nom.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomTextSecondary
import com.example.nom.ui.theme.NomTheme

@Composable
fun LoadingState(message: String = "") {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = NomGreenAccent, strokeWidth = 3.dp)
        if (message.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(message, style = MaterialTheme.typography.bodyMedium, color = NomTextSecondary)
        }
    }
}

@Preview
@Composable
fun LoadingStatePreview() {
    NomTheme {
        LoadingState("Loading Nom...")
    }
}
