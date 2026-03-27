package com.example.nom.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nom.core.domain.models.EvolutionStage
import com.example.nom.core.domain.models.Spirit

@Entity(tableName = "spirits")
data class SpiritEntity(
    @PrimaryKey val id: String,
    val name: String,
    val evolutionStage: Int,
    val xp: Int,
    val hunger: Float,
    val happiness: Float,
    val energy: Float
)

fun SpiritEntity.toDomain() = Spirit(
    id = id,
    name = name,
    evolutionStage = EvolutionStage(evolutionStage),
    xp = xp,
    hunger = hunger,
    happiness = happiness,
    energy = energy
)

fun Spirit.toEntity() = SpiritEntity(
    id = id,
    name = name,
    evolutionStage = evolutionStage.stage,
    xp = xp,
    hunger = hunger,
    happiness = happiness,
    energy = energy
)
