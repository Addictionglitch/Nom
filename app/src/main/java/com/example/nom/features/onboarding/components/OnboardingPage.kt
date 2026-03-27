package com.example.nom.features.onboarding.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OnboardingPage(
    title: String,
    description: String,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = title)
        Text(text = description)
    }
}
