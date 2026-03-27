package com.example.nom.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [SpiritEntity::class, PlantEntity::class, ScanHistoryEntity::class], version = 1)
@TypeConverters(com.example.nom.core.data.local.TypeConverters::class)
abstract class NomDatabase : RoomDatabase() {

    abstract fun spiritDao(): SpiritDao
    abstract fun plantDao(): PlantDao
    abstract fun scanHistoryDao(): ScanHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: NomDatabase? = null

        fun getInstance(context: Context, passphrase_char_array: CharArray): NomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    val factory = SupportFactory(SQLiteDatabase.getBytes(passphrase_char_array))
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NomDatabase::class.java,
                        "nom_database"
                    )
                        .openHelperFactory(factory)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
