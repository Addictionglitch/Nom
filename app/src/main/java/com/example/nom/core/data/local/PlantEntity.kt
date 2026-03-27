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
    val type: PlantType,
    val rarity: Rarity,
    val nutritionValue: Int,
    val happinessEffect: Float,
    val isToxic: Boolean,
    val imageUri: String,
    val scanLocationLat: Double? = null,
    val scanLocationLng: Double? = null,
    val firstDiscoveredAt: Long,
    val timesScanned: Int = 1
)

fun PlantEntity.toDomain() = Plant(
    id = id,
    commonName = commonName,
    scientificName = scientificName,
    type = type,
    rarity = rarity,
    nutritionValue = nutritionValue,
    happinessEffect = happinessEffect,
    isToxic = isToxic,
    imageUri = imageUri,
    scanLocation = if (scanLocationLat != null && scanLocationLng != null) {
        LatLng(scanLocationLat, scanLocationLng)
    } else null,
    firstDiscoveredAt = firstDiscoveredAt,
    timesScanned = timesScanned
)

fun Plant.toEntity() = PlantEntity(
    id = id,
    commonName = commonName,
    scientificName = scientificName,
    type = type,
    rarity = rarity,
    nutritionValue = nutritionValue,
    happinessEffect = happinessEffect,
    isToxic = isToxic,
    imageUri = imageUri,
    scanLocationLat = scanLocation?.latitude,
    scanLocationLng = scanLocation?.longitude,
    firstDiscoveredAt = firstDiscoveredAt,
    timesScanned = timesScanned
)
