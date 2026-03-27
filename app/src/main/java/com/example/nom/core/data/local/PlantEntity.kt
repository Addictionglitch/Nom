package com.example.nom.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nom.core.domain.models.LatLng
import com.example.nom.core.domain.models.Plant
import com.example.nom.core.domain.models.PlantType
import com.example.nom.core.domain.models.Rarity

@Entity(tableName = "plants")
data class PlantEntity(
    @PrimaryKey val id: String,
    val commonName: String,
    val scientificName: String,
    val imageUrl: String,
    val description: String,
    val type: PlantType,
    val rarity: Rarity,
    val location: LatLng
)

fun PlantEntity.toDomain() = Plant(
    id = id,
    commonName = commonName,
    scientificName = scientificName,
    imageUrl = imageUrl,
    description = description,
    type = type,
    rarity = rarity,
    location = location
)

fun Plant.toEntity() = PlantEntity(
    id = id,
    commonName = commonName,
    scientificName = scientificName,
    imageUrl = imageUrl,
    description = description,
    type = type,
    rarity = rarity,
    location = location
)
