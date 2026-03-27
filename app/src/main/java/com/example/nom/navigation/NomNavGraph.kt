package com.example.nom.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nom.core.data.local.SecurePreferences
import com.example.nom.features.evolution.EvolutionTreeScreen
import com.example.nom.features.evolution.SpiritStatusScreen
import com.example.nom.features.journal.JournalScreen
import com.example.nom.features.journal.PlantDetailScreen
import com.example.nom.features.minigame.MinigameScreen
import com.example.nom.features.minigame.garden.GardenMinigameScreen
import com.example.nom.features.onboarding.OnboardingScreen
import com.example.nom.features.profile.AuthScreen
import com.example.nom.features.profile.ProfileScreen
import com.example.nom.features.profile.SettingsScreen
import com.example.nom.features.scanresult.ScanResultScreen
import com.example.nom.features.scanner.ScannerScreen
import com.example.nom.features.spirit.SpiritScreen

@Composable
fun NomNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    securePreferences: SecurePreferences
) {
    NavHost(
        navController = navController,
        startDestination = if (securePreferences.onboardingCompleted) NomRoutes.Spirit.route else NomRoutes.Onboarding.route
    ) {
        composable(NomRoutes.Spirit.route) {
            Box(modifier = Modifier.padding(paddingValues)) {
                SpiritScreen(
                    onScanClicked = { navController.navigate(NomRoutes.Scanner.route) },
                    onEvolutionClicked = { navController.navigate(NomRoutes.Evolution.route) }
                )
            }
        }
        composable(NomRoutes.Scanner.route) {
            Box(modifier = Modifier.padding(paddingValues)) {
                ScannerScreen(navController)
            }
        }
        composable(
            route = NomRoutes.ScanResult.route,
            arguments = NomRoutes.ScanResult.arguments
        ) {
            Box(modifier = Modifier.padding(paddingValues)) {
                ScanResultScreen(onContinueClicked = { navController.popBackStack(NomRoutes.Spirit.route, false) })
            }
        }
        composable(NomRoutes.Journal.route) {
            Box(modifier = Modifier.padding(paddingValues)) {
                JournalScreen(navController, hiltViewModel())
            }
        }
        composable(
            route = NomRoutes.PlantDetail.route,
            arguments = NomRoutes.PlantDetail.arguments
        ) {
            Box(modifier = Modifier.padding(paddingValues)) {
                PlantDetailScreen(navController = navController, viewModel = hiltViewModel())
            }
        }
        composable(NomRoutes.Evolution.route) {
            Box(modifier = Modifier.padding(paddingValues)) {
                EvolutionTreeScreen()
            }
        }
        composable(NomRoutes.SpiritStatus.route) {
            Box(modifier = Modifier.padding(paddingValues)) {
                SpiritStatusScreen()
            }
        }
        composable(NomRoutes.Minigame.route) {
            Box(modifier = Modifier.padding(paddingValues)) {
                MinigameScreen(navController)
            }
        }
        composable(NomRoutes.Onboarding.route) {
            Box(modifier = Modifier.padding(paddingValues)) {
                OnboardingScreen(navController)
            }
        }
        composable(NomRoutes.Profile.route) {
            Box(modifier = Modifier.padding(paddingValues)) {
                ProfileScreen(navController)
            }
        }
        composable(NomRoutes.Settings.route) {
            Box(modifier = Modifier.padding(paddingValues)) {
                SettingsScreen(navController)
            }
        }
        composable(NomRoutes.Auth.route) {
            Box(modifier = Modifier.padding(paddingValues)) {
                AuthScreen()
            }
        }
        composable(NomRoutes.GardenMinigame.route) {
            Box(modifier = Modifier.padding(paddingValues)) {
                GardenMinigameScreen(navController)
            }
        }
    }
}
