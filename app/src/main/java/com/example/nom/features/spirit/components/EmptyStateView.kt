package com.example.nom.features.spirit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nom.core.domain.models.SpiritEmotion
import com.example.nom.ui.components.NomButton
import com.example.nom.ui.components.NomCard
import com.example.nom.ui.theme.NomTextSecondary
import com.example.nom.ui.theme.NomTheme

@Composable
fun EmptyStateView(onScanClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpiritView(emotion = SpiritEmotion.CURIOUS, evolutionStage = 0)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        NomCard(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Your Spirit is waiting",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Scan your first plant to start the journey",
                style = MaterialTheme.typography.bodyMedium,
                color = NomTextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        NomButton(
            onClick = onScanClicked,
            text = "Scan Your First Plant",
            icon = Icons.Outlined.CameraAlt
        )
    }
}

@Preview
@Composable
fun EmptyStateViewPreview() {
    NomTheme {
        EmptyStateView {}
    }
}
