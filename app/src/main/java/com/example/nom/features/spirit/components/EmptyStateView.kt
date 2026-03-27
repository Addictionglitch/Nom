package com.example.nom.features.spirit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nom.core.domain.models.SpiritEmotion
import com.example.nom.ui.components.NomButton
import com.example.nom.ui.components.NomCard

@Composable
fun EmptyStateView(onScanClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpiritView(emotion = SpiritEmotion.CURIOUS, evolutionStage = 1)
        NomCard(modifier = Modifier.padding(16.dp)) {
            Text("Scan your first plant to feed your Spirit!")
        }
        NomButton(onClick = onScanClicked, text = "Scan Plant")
    }
}

@Preview
@Composable
fun EmptyStateViewPreview() {
    EmptyStateView {}
}
