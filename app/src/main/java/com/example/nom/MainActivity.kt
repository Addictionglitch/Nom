package com.example.nom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.example.nom.core.data.local.SecurePreferences
import com.example.nom.navigation.BottomNavBar
import com.example.nom.navigation.NomNavGraph
import com.example.nom.ui.theme.NomTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var securePreferences: SecurePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NomTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavBar(navController = navController) }
                ) { paddingValues ->
                    NomNavGraph(
                        securePreferences = securePreferences,
                        navController = navController,
                        paddingValues = paddingValues
                    )
                }
            }
        }
    }
}
