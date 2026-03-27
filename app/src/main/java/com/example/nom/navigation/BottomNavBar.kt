package com.example.nom.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarRoutes = setOf(
        NomRoutes.Spirit.route,
        NomRoutes.Journal.route,
        NomRoutes.Minigame.route,
        NomRoutes.Profile.route
    )

    if (currentRoute in bottomBarRoutes) {
        BottomAppBar(
            actions = {
                IconButton(onClick = { navController.navigate(NomRoutes.Spirit.route) }) {
                    Icon(
                        Icons.Default.Home,
                        contentDescription = "Spirit",
                        tint = if (currentRoute == NomRoutes.Spirit.route) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
                IconButton(onClick = { navController.navigate(NomRoutes.Journal.route) }) {
                    Icon(
                        Icons.Default.MenuBook,
                        contentDescription = "Journal",
                        tint = if (currentRoute == NomRoutes.Journal.route) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
                IconButton(onClick = { navController.navigate(NomRoutes.Minigame.route) }) {
                    Icon(
                        Icons.Default.SportsEsports,
                        contentDescription = "Minigame",
                        tint = if (currentRoute == NomRoutes.Minigame.route) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
                IconButton(onClick = { navController.navigate(NomRoutes.Profile.route) }) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = if (currentRoute == NomRoutes.Profile.route) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate(NomRoutes.Scanner.route) },
                    containerColor = MaterialTheme.colorScheme.tertiary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Scanner")
                }
            }
        )
    }
}
