package com.example.nom.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NomButton(onClick: () -> Unit, text: String) {
    Button(onClick = onClick) {
        Text(text)
    }
}

@Preview
@Composable
fun NomButtonPreview() {
    NomButton(onClick = {}, text = "Click me")
}
