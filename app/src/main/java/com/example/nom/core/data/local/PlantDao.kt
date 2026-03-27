package com.example.nom.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nom.core.domain.models.PlantType
import com.example.nom.core.domain.models.Rarity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {
    @Query("SELECT * FROM plants")
    fun getAllPlants(): Flow<List<PlantEntity>>

    @Query("SELECT * FROM plants WHERE id = :id")
    suspend fun getPlantById(id: String): PlantEntity?

    @Query("SELECT * FROM plants WHERE type = :type")
    fun getPlantsByType(type: PlantType): Flow<List<PlantEntity>>

    @Query("SELECT * FROM plants WHERE rarity = :rarity")
    fun getPlantsByRarity(rarity: Rarity): Flow<List<PlantEntity>>

    @Query("SELECT * FROM plants WHERE name LIKE '%' || :query || '%'")
    fun searchPlants(query: String): Flow<List<PlantEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<PlantEntity>)
}
