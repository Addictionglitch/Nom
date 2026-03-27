package com.example.nom.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomDarkSurface
import com.example.nom.ui.theme.NomGlassBorder
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomTextMuted

@Composable
fun BottomNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val hiddenRoutes = setOf(
        NomRoutes.Scanner.route,
        NomRoutes.ScanResult.route,
        NomRoutes.Onboarding.route,
        NomRoutes.PlantDetail.route,
        NomRoutes.GardenMinigame.route,
        NomRoutes.Settings.route,
        NomRoutes.Auth.route
    )

    if (currentRoute !in hiddenRoutes) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp) // Wait, it needs to accommodate the offset FAB visually, but we can do it with an offset in the FAB itself and make sure parent doesn't clip. Actually for standard compose, overlapping children might be clipped if we don't draw carefully, but Scaffolds bottom bar slot allows drawing outside bounds usually. Let's trace it.
        ) {
            // Glass background
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .align(Alignment.BottomCenter)
                    .background(NomDarkSurface.copy(alpha = 0.85f))
                    .border(width = 1.dp, color = NomGlassBorder) // Only top border usually, but standard border draws all around
            ) {
                // Tab 1: Home
                NavBarItem(
                    icon = Icons.Outlined.Home,
                    label = "Spirit",
                    route = NomRoutes.Spirit.route,
                    currentRoute = currentRoute,
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )

                // Tab 2: Journal
                NavBarItem(
                    icon = Icons.Outlined.MenuBook,
                    label = "Journal",
                    route = NomRoutes.Journal.route,
                    currentRoute = currentRoute,
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )

                // Tab 3: Scanner FAB slot (Empty space)
                Box(modifier = Modifier.weight(1f))

                // Tab 4: Minigame
                NavBarItem(
                    icon = Icons.Outlined.SportsEsports,
                    label = "Play",
                    route = NomRoutes.Minigame.route,
                    currentRoute = currentRoute,
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )

                // Tab 5: Profile
                NavBarItem(
                    icon = Icons.Outlined.Person,
                    label = "Profile",
                    route = NomRoutes.Profile.route,
                    currentRoute = currentRoute,
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            }

            // Scanner FAB
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    // We offset it so it pops up above the bar
                    .offset(y = (-12).dp)
                    .size(60.dp)
                    .shadow(
                        elevation = 12.dp,
                        shape = CircleShape,
                        ambientColor = NomGreenAccent.copy(alpha = 0.4f),
                        spotColor = NomGreenAccent.copy(alpha = 0.4f)
                    )
                    .clip(CircleShape)
                    .background(NomGreenAccent)
                    .clickable {
                        navController.navigate(NomRoutes.Scanner.route)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.CameraAlt,
                    contentDescription = "Scan",
                    tint = NomDarkBg,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
private fun NavBarItem(
    icon: ImageVector,
    label: String,
    route: String,
    currentRoute: String?,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val selected = currentRoute == route
    val color = if (selected) NomGreenAccent else NomTextMuted

    Column(
        modifier = modifier
            .height(72.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                navController.navigate(route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, contentDescription = label, tint = color, modifier = Modifier.size(24.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
        )
        if (selected) {
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(NomGreenAccent)
            )
        } else {
            // Keep height consistent
            Box(modifier = Modifier.padding(top = 4.dp).size(4.dp))
        }
    }
}
