package com.example.nom.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nom.core.data.local.SecurePreferences
import com.example.nom.features.journal.JournalScreen
import com.example.nom.features.journal.PlantDetailScreen

@Composable
fun NomNavGraph(
    securePreferences: SecurePreferences,
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    val startDestination = if (securePreferences.onboardingCompleted) NomRoutes.Spirit.route else NomRoutes.Onboarding.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(NomRoutes.Spirit.route) { /* TODO */ }
        composable(NomRoutes.Scanner.route) {
            navController.popBackStack(NomRoutes.Spirit.route, false)
        }
        composable(
            NomRoutes.ScanResult.route,
            arguments = NomRoutes.ScanResult.arguments
        ) {
            navController.popBackStack(NomRoutes.Spirit.route, false)
        }
        composable(NomRoutes.Journal.route) {
            JournalScreen(navController = navController, viewModel = hiltViewModel())
        }
        composable(
            NomRoutes.PlantDetail.route,
            arguments = NomRoutes.PlantDetail.arguments
        ) {
            PlantDetailScreen(navController = navController, viewModel = hiltViewModel())
        }
        composable(NomRoutes.Evolution.route) { /* TODO */ }
        composable(NomRoutes.SpiritStatus.route) { /* TODO */ }
        composable(NomRoutes.Minigame.route) { /* TODO */ }
        composable(NomRoutes.Onboarding.route) {
            // navigate to Spirit and clear backstack
            navController.navigate(NomRoutes.Spirit.route) {
                popUpTo(NomRoutes.Onboarding.route) { inclusive = true }
                launchSingleTop = true
            }
        }
        composable(NomRoutes.Profile.route) { /* TODO */ }
        composable(NomRoutes.Settings.route) { /* TODO */ }
        composable(NomRoutes.Auth.route) { /* TODO */ }
    }
}
