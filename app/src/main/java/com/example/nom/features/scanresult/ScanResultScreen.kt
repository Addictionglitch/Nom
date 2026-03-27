package com.example.nom.features.scanresult

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nom.core.domain.models.XpBreakdown
import com.example.nom.features.scanresult.components.DiscoveryBanner
import com.example.nom.features.scanresult.components.PlantCard
import com.example.nom.features.scanresult.components.ToxicWarning
import com.example.nom.features.scanresult.components.XpAnimation
import com.example.nom.ui.components.ErrorState
import com.example.nom.ui.components.LoadingState
import com.example.nom.ui.components.NomButton
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomGreenAccent

@Composable
fun ScanResultScreen(
    viewModel: ScanResultViewModel = hiltViewModel(),
    onContinueClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NomDarkBg)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = uiState) {
                is ScanResultUiState.Loading -> LoadingState()
                is ScanResultUiState.Error -> ErrorState(message = state.message)
                is ScanResultUiState.PositiveResult -> {
                    ResultContent(
                        state.scanResult.plant,
                        state.scanResult.confidence,
                        state.scanResult.xpGained,
                        state.scanResult.xpBreakdown,
                        state.scanResult.spiritReaction.name,
                        state.hasFed,
                        viewModel::feedSpirit,
                        onContinueClicked
                    )
                }
                is ScanResultUiState.NewDiscovery -> {
                    DiscoveryBanner(visible = true)
                    ResultContent(
                        state.scanResult.plant,
                        state.scanResult.confidence,
                        state.scanResult.xpGained,
                        state.scanResult.xpBreakdown,
                        state.scanResult.spiritReaction.name,
                        state.hasFed,
                        viewModel::feedSpirit,
                        onContinueClicked
                    )
                }
                is ScanResultUiState.ToxicResult -> {
                    ToxicWarning(visible = true)
                    ResultContent(
                        state.scanResult.plant,
                        state.scanResult.confidence,
                        0,
                        XpBreakdown(0,0,0),
                        state.scanResult.spiritReaction.name,
                        state.hasFed,
                        viewModel::feedSpirit,
                        onContinueClicked
                    )
                }
            }
        }
    }
}

@Composable
private fun ResultContent(
    plant: com.example.nom.core.domain.models.Plant,
    confidence: Float,
    xpGained: Int,
    xpBreakdown: XpBreakdown,
    spiritReaction: String,
    hasFed: Boolean,
    onFeedClicked: () -> Unit,
    onContinueClicked: () -> Unit
) {
    PlantCard(plant = plant, confidence = confidence, isToxic = plant.isToxic)
    Spacer(modifier = Modifier.height(16.dp))

    if (xpGained > 0) {
        XpAnimation(xpGained = xpGained)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${xpBreakdown.baseXp} base + ${xpBreakdown.rarityBonus} rarity + ${xpBreakdown.discoveryBonus} discovery = ${xpGained} XP",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "Your Spirit feels ${spiritReaction.lowercase()}!",
        color = NomGreenAccent,
        fontSize = 18.sp
    )
    Spacer(modifier = Modifier.height(24.dp))

    if (hasFed) {
        Text("Spirit fed!", color = Color.White, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        NomButton(onClick = onContinueClicked, text = "Continue")
    } else {
        NomButton(onClick = onFeedClicked, text = "Feed")
    }
}
