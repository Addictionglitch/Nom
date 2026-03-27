package com.example.nom.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NomRoutes(val route: String) {
    object Spirit : NomRoutes("spirit")
    object Scanner : NomRoutes("scanner")
    object ScanResult : NomRoutes("scan_result/{scanId}") {
        fun createRoute(scanId: String) = "scan_result/$scanId"
        val arguments = listOf(navArgument("scanId") { type = NavType.StringType })
    }
    object Journal : NomRoutes("journal")
    object PlantDetail : NomRoutes("plant_detail/{plantId}") {
        fun createRoute(plantId: String) = "plant_detail/$plantId"
        val arguments = listOf(navArgument("plantId") { type = NavType.StringType })
    }
    object Evolution : NomRoutes("evolution")
    object SpiritStatus : NomRoutes("spirit_status")
    object Minigame : NomRoutes("minigame")
    object Onboarding : NomRoutes("onboarding")
    object Profile : NomRoutes("profile")
    object Settings : NomRoutes("settings")
    object Auth : NomRoutes("auth")
}
