package com.example.nom.features.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {
    Column {
        Button(onClick = { /* Stub */ }) {
            Text("Sign in with Google")
        }
        Button(onClick = { /* Stub */ }) {
            Text("Sign in with Apple")
        }
        Button(onClick = viewModel::onContinueWithoutAccount) {
            Text("Continue without account")
        }
    }
}
