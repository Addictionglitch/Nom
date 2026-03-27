package com.example.nom.features.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nom.ui.components.NomGlowBackground
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomTextSecondary

@Composable
fun OnboardingPage(
    title: String,
    description: String,
    illustration: @Composable () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NomDarkBg)
    ) {
        // Subtle top glow
        NomGlowBackground(modifier = Modifier.align(Alignment.TopCenter))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Illustration zone — upper 45% of screen
            Box(
                modifier = Modifier
                    .weight(0.45f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                illustration()
            }

            // Text zone — lower portion
            Column(
                modifier = Modifier.weight(0.35f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displayMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = NomTextSecondary,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            }

            Spacer(modifier = Modifier.weight(0.2f))
        }
    }
}
