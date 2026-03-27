package com.example.nom.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nom.core.domain.models.Plant
import com.example.nom.core.domain.models.ScanResult
import com.example.nom.core.domain.models.SpiritEmotion

@Entity(tableName = "scan_history")
data class ScanHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val plantSnapshot: String,
    val timestamp: Long,
    val isSynced: Boolean = false,
    val confidence: Float,
    val isNewDiscovery: Boolean,
    val xpGained: Int,
    val spiritReaction: String
)

fun ScanHistoryEntity.toDomain(plant: Plant) = ScanResult(
    id = id.toString(),
    plant = plant,
    timestamp = timestamp,
    confidence = confidence,
    isNewDiscovery = isNewDiscovery,
    xpGained = xpGained,
    spiritReaction = SpiritEmotion.valueOf(spiritReaction)
)

fun ScanResult.toEntity(plantSnapshotJson: String) = ScanHistoryEntity(
    plantSnapshot = plantSnapshotJson,
    timestamp = timestamp,
    isSynced = false,
    confidence = confidence,
    isNewDiscovery = isNewDiscovery,
    xpGained = xpGained,
    spiritReaction = spiritReaction.name
)
