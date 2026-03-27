package com.example.nom.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SpiritDao {
    @Query("SELECT * FROM spirits LIMIT 1")
    fun getSpirit(): Flow<SpiritEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpirit(spirit: SpiritEntity)

    @Update
    suspend fun updateSpirit(spirit: SpiritEntity)
}
