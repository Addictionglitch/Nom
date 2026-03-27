package com.example.nom.features.journal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.nom.ui.theme.NomDarkSurface
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomTextPrimary

@Composable
fun SpeciesCounter(discovered: Int, total: Int) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(NomDarkSurface)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Eco,
            contentDescription = null,
            tint = NomGreenAccent,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$discovered / $total Discovered",
            style = MaterialTheme.typography.labelLarge,
            color = NomTextPrimary
        )
    }
}
