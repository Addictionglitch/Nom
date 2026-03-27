package com.example.nom.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NomTopBar(title: String) {
    TopAppBar(
        title = { Text(title) }
    )
}

@Preview
@Composable
fun NomTopBarPreview() {
    NomTopBar(title = "Nom")
}
