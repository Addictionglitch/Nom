package com.example.nom.core.data.local

import androidx.room.TypeConverter
import com.example.nom.core.domain.models.LatLng
import com.example.nom.core.domain.models.PlantType
import com.example.nom.core.domain.models.Rarity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Type converters to allow Room to reference complex data types.
 */
class TypeConverters {

    private val gson = Gson()

    /**
     * Converts a string to a LatLng object.
     * @param value The string representation of the LatLng, e.g., "lat,lng".
     * @return The LatLng object, or null if the string is malformed.
     */
    @TypeConverter
    fun fromLatLngString(value: String?): LatLng? {
        val parts = value?.split(',')
        return if (parts?.size == 2) {
            LatLng(parts[0].toDouble(), parts[1].toDouble())
        } else {
            null
        }
    }

    /**
     * Converts a LatLng object to its string representation.
     * @param latLng The LatLng object.
     * @return The string representation, e.g., "lat,lng".
     */
    @TypeConverter
    fun toLatLngString(latLng: LatLng?): String? {
        return latLng?.let { "${it.latitude},${it.longitude}" }
    }

    /**
     * Converts a string to a Rarity enum.
     * @param value The string name of the enum.
     * @return The Rarity enum.
     */
    @TypeConverter
    fun fromRarity(value: String?): Rarity? {
        return value?.let { Rarity.valueOf(it) }
    }

    /**
     * Converts a Rarity enum to its string name.
     * @param rarity The Rarity enum.
     * @return The string name of the enum.
     */
    @TypeConverter
    fun toRarity(rarity: Rarity?): String? {
        return rarity?.name
    }

    /**
     * Converts a string to a PlantType enum.
     * @param value The string name of the enum.
     * @return The PlantType enum.
     */
    @TypeConverter
    fun fromPlantType(value: String?): PlantType? {
        return value?.let { PlantType.valueOf(it) }
    }

    /**
     * Converts a PlantType enum to its string name.
     * @param plantType The PlantType enum.
     * @return The string name of the enum.
     */
    @TypeConverter
    fun toPlantType(plantType: PlantType?): String? {
        return plantType?.name
    }

    /**
     * Converts a JSON string to a Map<PlantType, Float>.
     * @param value The JSON string.
     * @return The Map object.
     */
    @TypeConverter
    fun fromPlantTypeMap(value: String?): Map<PlantType, Float>? {
        val mapType = object : TypeToken<Map<PlantType, Float>>() {}.type
        return gson.fromJson(value, mapType)
    }

    /**
     * Converts a Map<PlantType, Float> to a JSON string.
     * @param map The Map object.
     * @return The JSON string.
     */
    @TypeConverter
    fun toPlantTypeMap(map: Map<PlantType, Float>?): String? {
        return gson.toJson(map)
    }
}
