package com.example.nom.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nom.core.domain.models.ScanResult

@Entity(tableName = "scan_history")
data class ScanHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val plantId: String,
    val timestamp: Long,
    val isSynced: Boolean = false
)

fun ScanHistoryEntity.toDomain(plant: com.example.nom.core.domain.models.Plant) = ScanResult(
    plant = plant,
    timestamp = timestamp
)

fun ScanResult.toEntity() = ScanHistoryEntity(
    plantId = plant.id,
    timestamp = timestamp,
    isSynced = false
)
