package com.example.nom.features.journal.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nom.core.domain.models.PlantType
import com.example.nom.core.domain.models.Rarity

@OptIn(ExperimentalMaterial3Api::class)
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
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                onSearchQueryChanged(it)
            },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(PlantType.values()) { type ->
                    FilterChip(
                        selected = selectedPlantType == type,
                        onClick = {
                            selectedPlantType = if (selectedPlantType == type) null else type
                            onFilterChanged(selectedPlantType, selectedRarity)
                        },
                        label = { Text(type.name.lowercase().replaceFirstChar { it.uppercase() }) }
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("Rarity")
            DropdownMenu(
                expanded = showRarityDropdown,
                onDismissRequest = { showRarityDropdown = false }
            ) {
                Rarity.values().forEach { rarity ->
                    DropdownMenuItem(text = { Text(rarity.name) }, onClick = {
                        selectedRarity = rarity
                        onFilterChanged(selectedPlantType, selectedRarity)
                        showRarityDropdown = false
                    })
                }
            }
        }
    }
}
