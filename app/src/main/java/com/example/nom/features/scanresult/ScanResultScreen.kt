package com.example.nom.features.scanresult

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nom.features.scanresult.components.DiscoveryBanner
import com.example.nom.features.scanresult.components.PlantCard
import com.example.nom.features.scanresult.components.ToxicWarning
import com.example.nom.features.scanresult.components.XpAnimation
import com.example.nom.ui.components.ErrorState
import com.example.nom.ui.components.LoadingState
import com.example.nom.ui.components.NomButton

@Composable
fun ScanResultScreen(
    viewModel: ScanResultViewModel = hiltViewModel(),
    onContinueClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = uiState) {
            is ScanResultUiState.Loading -> LoadingState()
            is ScanResultUiState.Error -> ErrorState(message = state.message)
            is ScanResultUiState.PositiveResult -> {
                PlantCard(
                    plant = state.scanResult.plant,
                    confidence = state.scanResult.confidence,
                    isToxic = false
                )
                XpAnimation(xpGained = state.scanResult.xpGained)
                Spacer(modifier = Modifier.height(16.dp))
                NomButton(onClick = { viewModel.feedSpirit() }, text = "Feed")
            }
            is ScanResultUiState.NewDiscovery -> {
                DiscoveryBanner(visible = true)
                PlantCard(
                    plant = state.scanResult.plant,
                    confidence = state.scanResult.confidence,
                    isToxic = false
                )
                XpAnimation(xpGained = state.scanResult.xpGained)
                Spacer(modifier = Modifier.height(16.dp))
                NomButton(onClick = { viewModel.feedSpirit() }, text = "Feed")
            }
            is ScanResultUiState.ToxicResult -> {
                ToxicWarning(visible = true)
                PlantCard(
                    plant = state.scanResult.plant,
                    confidence = state.scanResult.confidence,
                    isToxic = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                NomButton(onClick = { viewModel.feedSpirit() }, text = "Feed")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        NomButton(onClick = onContinueClicked, text = "Continue")
    }
}
