package com.example.nom.features.journal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.nom.core.domain.models.PlantType
import com.example.nom.core.domain.models.Rarity
import com.example.nom.ui.components.NomTextField
import com.example.nom.ui.theme.NomDarkCard
import com.example.nom.ui.theme.NomDarkSurface
import com.example.nom.ui.theme.NomGlassBorder
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomTextPrimary
import com.example.nom.ui.theme.NomTextSecondary

@Composable
fun JournalFilterBar(
    onFilterChanged: (PlantType?, Rarity?) -> Unit,
    onSearchQueryChanged: (String) -> Unit
) {
    var selectedPlantType by remember { mutableStateOf<PlantType?>(null) }
    var selectedRarity by remember { mutableStateOf<Rarity?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var showRarityDropdown by remember { mutableStateOf(false) }

    Column {
        NomTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                onSearchQueryChanged(it)
            },
            label = "Search Species",
            leadingIcon = Icons.Outlined.Search
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            LazyRow(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(PlantType.values()) { type ->
                    JournalFilterChip(
                        selected = selectedPlantType == type,
                        label = type.name.lowercase().replaceFirstChar { it.uppercase() },
                        onClick = {
                            selectedPlantType = if (selectedPlantType == type) null else type
                            onFilterChanged(selectedPlantType, selectedRarity)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Box {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .background(if (selectedRarity != null) NomGreenAccent.copy(alpha = 0.2f) else NomDarkSurface)
                        .border(
                            1.dp,
                            if (selectedRarity != null) NomGreenAccent else NomGlassBorder,
                            RoundedCornerShape(100.dp)
                        )
                        .clickable { showRarityDropdown = true }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = selectedRarity?.name ?: "Rarity",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (selectedRarity != null) NomGreenAccent else NomTextSecondary
                    )
                }
                DropdownMenu(
                    expanded = showRarityDropdown,
                    onDismissRequest = { showRarityDropdown = false },
                    modifier = Modifier.background(NomDarkCard)
                ) {
                    DropdownMenuItem(
                        text = { Text("All", color = NomTextPrimary) },
                        onClick = {
                            selectedRarity = null
                            onFilterChanged(selectedPlantType, null)
                            showRarityDropdown = false
                        }
                    )
                    Rarity.values().forEach { rarity ->
                        DropdownMenuItem(
                            text = { Text(rarity.name, color = NomTextPrimary) },
                            onClick = {
                                selectedRarity = rarity
                                onFilterChanged(selectedPlantType, selectedRarity)
                                showRarityDropdown = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun JournalFilterChip(selected: Boolean, label: String, onClick: () -> Unit) {
    val bgColor = if (selected) NomGreenAccent.copy(alpha = 0.2f) else NomDarkSurface
    val borderColor = if (selected) NomGreenAccent else NomGlassBorder
    val textColor = if (selected) NomGreenAccent else NomTextPrimary

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(100.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = textColor)
    }
}
