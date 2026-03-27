package com.example.nom.features.journal.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SpeciesCounter(discovered: Int, total: Int) {
    Text(text = "$discovered/$total discovered")
}
