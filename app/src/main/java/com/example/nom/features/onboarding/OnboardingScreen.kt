package com.example.nom.features.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.nom.ui.theme.NomGlassBorder
import com.example.nom.ui.theme.NomGlassFill
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomTealSpirit
import com.example.nom.ui.theme.NomTextMuted
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
                    OnboardingPage(
                        title = "Ready to Grow?",
                        description = "Join our community of plant enthusiasts. Save your discoveries and watch your digital garden flourish.",
                        illustration = {
                            Box(
                                modifier = Modifier
                                    .size(width = 240.dp, height = 360.dp)
                                    .clip(RoundedCornerShape(32.dp))
                                    .background(NomGlassFill)
                                    .border(2.dp, NomGlassBorder, RoundedCornerShape(32.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                SpiritView(emotion = SpiritEmotion.HAPPY, evolutionStage = 0)
                            }
                        }
                    )
                }
                1 -> {
                    OnboardingPage(
                        title = "Scan Real Plants",
                        description = "Point your camera at any plant, flower, or mushroom. Your Spirit learns and grows from every discovery.",
                        illustration = {
                            Icon(
                                Icons.Outlined.CameraAlt,
                                contentDescription = null,
                                tint = NomGreenAccent,
                                modifier = Modifier.size(80.dp)
                            )
                        }
                    )
                }
                2 -> {
                    OnboardingPage(
                        title = "Every Spirit is Unique",
                        description = "Your Spirit changes based on what you feed it. A mushroom diet creates a different Spirit than a flower diet.",
                        illustration = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val sizes = listOf(16.dp, 24.dp, 36.dp, 52.dp, 72.dp)
                                sizes.forEach { size ->
                                    Box(
                                        modifier = Modifier
                                            .size(size)
                                            .clip(CircleShape)
                                            .background(NomTealSpirit)
                                    )
                                }
                            }
                        }
                    )
                }
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
                // Wait, NomTextButton uses primary button normally? Standard text buttons should exist.
                // We'll use NomTextButton if it styles correctly, prompt says text-style NomTextSecondary.
                NomTextButton(onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(2)
                    }
                }, text = "Skip")
            } else {
                Box {}
            }

            // Dot pager
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(3) { index ->
                    val isActive = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .size(if (isActive) 8.dp else 6.dp)
                            .background(
                                color = if (isActive) NomGreenAccent else NomTextMuted,
                                shape = CircleShape
                            )
                    )
                }
            }

            if (pagerState.currentPage < 2) {
                NomTextButton(onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }, text = "Next")
            } else {
                NomButton(
                    onClick = viewModel::onShowNamingDialog,
                    text = "Name your Spirit",
                    modifier = Modifier.fillMaxWidth(0.5f)
                )
            }
        }
    }
}
