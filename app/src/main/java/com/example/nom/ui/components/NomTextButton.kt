package com.example.nom.ui.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NomTextButton(onClick: () -> Unit, text: String) {
    TextButton(onClick = onClick) {
        Text(text)
    }
}

@Preview
@Composable
fun NomTextButtonPreview() {
    NomTextButton(onClick = {}, text = "Click me")
}
