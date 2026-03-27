package com.example.nom.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nom.core.domain.models.PlantType
import com.example.nom.core.domain.models.Spirit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Represents a spirit in the local database.
 *
 * @property id The unique identifier of the spirit.
 * @property name The name of the spirit.
 * @property evolutionStage The current evolution stage of the spirit.
 * @property xp The experience points of the spirit.
 * @property hunger The hunger level of the spirit.
 * @property happiness The happiness level of the spirit.
 * @property energy The energy level of the spirit.
 * @property dietComposition A JSON string representing the diet composition of the spirit.
 * @property lastFedTimestamp The timestamp when the spirit was last fed.
 * @property streakDays The number of consecutive days the spirit has been fed.
 * @property totalScans The total number of scans performed.
 * @property speciesDiscovered The number of unique species discovered.
 * @property createdAt The timestamp when the spirit was created.
 * @property level The level of the spirit.
 */
@Entity(tableName = "spirits")
data class SpiritEntity(
    @PrimaryKey val id: String,
    val name: String,
    val evolutionStage: Int,
    val xp: Int,
    val hunger: Float,
    val happiness: Float,
    val energy: Float,
    val dietComposition: String,
    val lastFedTimestamp: Long,
    val streakDays: Int,
    val totalScans: Int,
    val speciesDiscovered: Int,
    val createdAt: Long,
    val level: Int
)

/**
 * Converts a [SpiritEntity] to a [Spirit] domain model.
 */
fun SpiritEntity.toDomain(): Spirit {
    val gson = Gson()
    val mapType = object : TypeToken<Map<PlantType, Float>>() {}.type
    val parsedDiet: Map<PlantType, Float> = try {
        if (dietComposition.isNotBlank()) gson.fromJson(dietComposition, mapType) else emptyMap()
    } catch (e: Exception) { emptyMap() }

    return Spirit(
        id = id,
        name = name,
        evolutionStage = evolutionStage,
        xp = xp,
        hunger = hunger,
        happiness = happiness,
        energy = energy,
        dietComposition = parsedDiet,
        lastFedTimestamp = lastFedTimestamp,
        streakDays = streakDays,
        totalScans = totalScans,
        speciesDiscovered = speciesDiscovered,
        createdAt = createdAt,
        level = level
    )
}

/**
 * Converts a [Spirit] to a [SpiritEntity] for database storage.
 */
fun Spirit.toEntity() = SpiritEntity(
    id = id,
    name = name,
    evolutionStage = evolutionStage,
    xp = xp,
    hunger = hunger,
    happiness = happiness,
    energy = energy,
    dietComposition = Gson().toJson(dietComposition),
    lastFedTimestamp = lastFedTimestamp,
    streakDays = streakDays,
    totalScans = totalScans,
    speciesDiscovered = speciesDiscovered,
    createdAt = createdAt,
    level = level
)
