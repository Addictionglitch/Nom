package com.example.nom.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanHistoryDao {
    @Query("SELECT * FROM scan_history ORDER BY timestamp DESC")
    fun getScanHistory(): Flow<List<ScanHistoryEntity>>

    /**
     * Retrieves all scans within a specific date range, ordered by the most recent first.
     * @param from The start timestamp of the range.
     * @param to The end timestamp of the range.
     * @return A Flow emitting a list of scan history entities within the given range.
     */
    @Query("SELECT * FROM scan_history WHERE timestamp BETWEEN :from AND :to ORDER BY timestamp DESC")
    fun getScansInDateRange(from: Long, to: Long): Flow<List<ScanHistoryEntity>>

    @Query("SELECT * FROM scan_history WHERE isSynced = 0")
    suspend fun getUnsyncedScans(): List<ScanHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScan(scan: ScanHistoryEntity)

    @Query("UPDATE scan_history SET isSynced = 1 WHERE id IN (:ids)")
    suspend fun markScansAsSynced(ids: List<Long>)
}
