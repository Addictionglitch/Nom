package com.example.nom.features.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nom.core.domain.models.SpiritEmotion
import com.example.nom.features.onboarding.components.OnboardingPage
import com.example.nom.features.onboarding.components.SpiritNamingDialog
import com.example.nom.features.spirit.components.SpiritView
import com.example.nom.navigation.NomRoutes
import com.example.nom.ui.components.NomButton
import com.example.nom.ui.components.NomTextButton
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomGreenAccent
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    navController: NavController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val showNamingDialog by viewModel.showNamingDialog.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.navigateToSpirit.collect {
            navController.navigate(NomRoutes.Spirit.route) {
                popUpTo(NomRoutes.Onboarding.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    if (showNamingDialog) {
        SpiritNamingDialog(
            onDismiss = viewModel::onDismissNamingDialog,
            onConfirm = viewModel::onNameSpirit
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NomDarkBg)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp, vertical = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth(0.6f),
                            contentAlignment = Alignment.Center
                        ) {
                            SpiritView(emotion = SpiritEmotion.HAPPY, evolutionStage = 0)
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        OnboardingPage(
                            "Welcome to Nom",
                            "Discover the world of plants and nurture your own digital spirit."
                        )
                    }
                }
                1 -> OnboardingPage("Scan & Identify", "Use your camera to identify any plant, flower, or tree around you.")
                2 -> OnboardingPage("Nurture & Evolve", "Feed your spirit with your discoveries and watch it grow and change.")
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (pagerState.currentPage < 2) {
                NomTextButton(onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(2)
                    }
                }, text = "Skip")
            } else {
                Box {}
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(3) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = if (pagerState.currentPage == index)
                                    NomGreenAccent
                                else Color.White.copy(alpha = 0.3f),
                                shape = CircleShape
                            )
                    )
                }
            }

            if (pagerState.currentPage < 2) {
                NomButton(onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }, text = "Next")
            } else {
                NomButton(onClick = viewModel::onShowNamingDialog, text = "Name your Spirit")
            }
        }
    }
}
