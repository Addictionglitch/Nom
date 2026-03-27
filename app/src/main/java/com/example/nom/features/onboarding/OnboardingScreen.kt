package com.example.nom.features.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nom.features.onboarding.components.OnboardingPage
import com.example.nom.features.onboarding.components.SpiritNamingDialog
import com.google.accompanist.pager.HorizontalPagerIndicator

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val showNamingDialog by viewModel.showNamingDialog.collectAsState()

    if (showNamingDialog) {
        SpiritNamingDialog(
            onDismiss = viewModel::onDismissNamingDialog,
            onConfirm = viewModel::onNameSpirit
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> OnboardingPage("Nom", "Feed your curiosity")
                1 -> OnboardingPage("Point your camera at any plant", "")
                2 -> OnboardingPage("Watch your Spirit grow", "")
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
                Button(onClick = { /* TODO: Skip */ }) {
                    Text("Skip")
                }
            } else {
                Box {}
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                pageCount = 3,
            )

            if (pagerState.currentPage < 2) {
                Button(onClick = { /* TODO: Next */ }) {
                    Text("Next")
                }
            } else {
                Button(onClick = viewModel::onShowNamingDialog) {
                    Text("Name your Spirit")
                }
            }
        }
    }
}
