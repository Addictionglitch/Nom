package com.example.nom.core.data.local

import android.content.Context
import com.example.nom.core.domain.models.Plant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * A class to handle loading a bundled offline plant cache into the Room database.
 *
 * @param context The application context.
 * @param database The Room database instance.
 */
class OfflinePlantCache(
    private val context: Context,
    private val database: NomDatabase
) {

    /**
     * Loads the offline plant cache into the database if it hasn't been loaded before.
     * This function is a suspend function and should be called from a coroutine.
     */
    suspend fun loadOfflinePlants() {
        val sharedPreferences = context.getSharedPreferences("offline_cache", Context.MODE_PRIVATE)
        val isCacheLoaded = sharedPreferences.getBoolean("is_loaded", false)

        if (!isCacheLoaded) {
            withContext(Dispatchers.IO) {
                try {
                    val json = context.assets.open("offline_plants.json").bufferedReader().use { it.readText() }
                    val plantListType = object : TypeToken<List<Plant>>() {}.type
                    val plants: List<Plant> = Gson().fromJson(json, plantListType)
                    database.plantDao().insertAll(plants.map { it.toEntity() })
                    sharedPreferences.edit().putBoolean("is_loaded", true).apply()
                } catch (e: Exception) {
                    // Handle exception
                }
            }
        }
    }
}
