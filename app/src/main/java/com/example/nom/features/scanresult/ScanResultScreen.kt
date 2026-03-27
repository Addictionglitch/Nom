package com.example.nom.features.scanresult

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nom.core.domain.models.XpBreakdown
import com.example.nom.features.scanresult.components.DiscoveryBanner
import com.example.nom.features.scanresult.components.PlantCard
import com.example.nom.features.scanresult.components.ToxicWarning
import com.example.nom.features.scanresult.components.XpAnimation
import com.example.nom.ui.components.ErrorState
import com.example.nom.ui.components.LoadingState
import com.example.nom.ui.components.NomButton
import com.example.nom.ui.components.NomGlowBackground
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomTextSecondary

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
    ) {
        NomGlowBackground(modifier = Modifier.align(Alignment.TopCenter))
        
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onContinueClicked,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.1f))
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack, 
                    contentDescription = "Close", 
                    tint = Color.White
                )
            }
            Text(
                text = "Result",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(40.dp)) // balance center
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 96.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            when (val state = uiState) {
                is ScanResultUiState.Loading -> LoadingState()
                is ScanResultUiState.Error -> ErrorState(message = state.message)
                is ScanResultUiState.PositiveResult -> {
                    ResultContent(
                        plant = state.scanResult.plant,
                        confidence = state.scanResult.confidence,
                        xpGained = state.scanResult.xpGained,
                        xpBreakdown = state.scanResult.xpBreakdown,
                        spiritReaction = state.scanResult.spiritReaction.name,
                        hasFed = state.hasFed,
                        isNewDiscovery = false,
                        onFeedClicked = viewModel::feedSpirit,
                        onContinueClicked = onContinueClicked
                    )
                }
                is ScanResultUiState.NewDiscovery -> {
                    ResultContent(
                        plant = state.scanResult.plant,
                        confidence = state.scanResult.confidence,
                        xpGained = state.scanResult.xpGained,
                        xpBreakdown = state.scanResult.xpBreakdown,
                        spiritReaction = state.scanResult.spiritReaction.name,
                        hasFed = state.hasFed,
                        isNewDiscovery = true,
                        onFeedClicked = viewModel::feedSpirit,
                        onContinueClicked = onContinueClicked
                    )
                }
                is ScanResultUiState.ToxicResult -> {
                    ResultContent(
                        plant = state.scanResult.plant,
                        confidence = state.scanResult.confidence,
                        xpGained = 0,
                        xpBreakdown = XpBreakdown(0, 0, 0),
                        spiritReaction = state.scanResult.spiritReaction.name,
                        hasFed = state.hasFed,
                        isNewDiscovery = false,
                        onFeedClicked = viewModel::feedSpirit,
                        onContinueClicked = onContinueClicked
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
    isNewDiscovery: Boolean,
    onFeedClicked: () -> Unit,
    onContinueClicked: () -> Unit
) {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isNewDiscovery) {
            DiscoveryBanner(visible = true)
        }
        
        if (plant.isToxic) {
            ToxicWarning(visible = true)
        }

        PlantCard(plant = plant, confidence = confidence, isToxic = plant.isToxic)

        if (xpGained > 0) {
            Spacer(modifier = Modifier.height(16.dp))
            XpAnimation(xpGained = xpGained)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${xpBreakdown.baseXp} base + ${xpBreakdown.rarityBonus} rarity + ${xpBreakdown.discoveryBonus} new",
                style = MaterialTheme.typography.bodySmall,
                color = NomTextSecondary,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        
        if (!plant.isToxic) {
            Text(
                text = "Spirit reaction: $spiritReaction",
                style = MaterialTheme.typography.titleSmall,
                color = NomGreenAccent
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            if (hasFed) {
                NomButton(
                    onClick = onContinueClicked,
                    text = "Continue",
                    modifier = Modifier.fillMaxWidth()
                )
            } else if (!plant.isToxic) {
                NomButton(
                    onClick = onFeedClicked,
                    text = "Feed to Spirit",
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                NomButton(
                    onClick = onContinueClicked,
                    text = "Close",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
