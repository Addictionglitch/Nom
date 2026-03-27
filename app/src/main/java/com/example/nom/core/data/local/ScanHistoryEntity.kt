package com.example.nom.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nom.core.domain.models.Plant
import com.example.nom.core.domain.models.ScanResult
import com.example.nom.core.domain.models.SpiritReaction

/**
 * Represents a scan history entry in the local database.
 *
 * @property id The unique identifier of the scan history entry.
 * @property plantSnapshot A JSON string representing the plant that was scanned.
 * @property timestamp The timestamp of the scan.
 * @property isSynced Whether the scan has been synced with the remote server.
 * @property confidence The confidence level of the scan result.
 * @property isNewDiscovery Whether the scan resulted in a new discovery.
 * @property xpGained The experience points gained from the scan.
 * @property spiritReaction The reaction of the spirit to the scan.
 */
@Entity(tableName = "scan_history")
data class ScanHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val plantSnapshot: String, // Store the full plant snapshot as a JSON string
    val timestamp: Long,
    val isSynced: Boolean = false,
    val confidence: Float,
    val isNewDiscovery: Boolean,
    val xpGained: Int,
    val spiritReaction: String
)

/**
 * Converts a [ScanHistoryEntity] to a [ScanResult] domain model.
 *
 * This function requires a [Plant] object, which can be constructed
 * from the [plantSnapshot] property.
 */
fun ScanHistoryEntity.toDomain(plant: Plant) = ScanResult(
    plant = plant,
    timestamp = timestamp,
    confidence = confidence,
    isNewDiscovery = isNewDiscovery,
    xpGained = xpGained,
    spiritReaction = SpiritReaction.valueOf(spiritReaction)
)

/**
 * Converts a [ScanResult] to a [ScanHistoryEntity] for database storage.
 */
fun ScanResult.toEntity(plantSnapshot: String) = ScanHistoryEntity(
    plantSnapshot = plantSnapshot,
    timestamp = timestamp,
    isSynced = false,
    confidence = confidence,
    isNewDiscovery = isNewDiscovery,
    xpGained = xpGained,
    spiritReaction = spiritReaction.name
)
