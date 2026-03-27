package com.example.nom.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

/**
 * The Room database for the application.
 *
 * This database stores information about spirits, plants, and scan history.
 * It uses SQLCipher for encryption.
 *
 * @see SpiritEntity
 * @see PlantEntity
 * @see ScanHistoryEntity
 * @see TypeConverters
 */
@Database(
    entities = [SpiritEntity::class, PlantEntity::class, ScanHistoryEntity::class],
    version = 2
)
@TypeConverters(com.example.nom.core.data.local.TypeConverters::class)
abstract class NomDatabase : RoomDatabase() {

    abstract fun spiritDao(): SpiritDao
    abstract fun plantDao(): PlantDao
    abstract fun scanHistoryDao(): ScanHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: NomDatabase? = null

        /**
         * Gets the singleton instance of the database.
         *
         * @param context The application context.
         * @param passphrase_char_array The passphrase for the encrypted database.
         * @return The singleton instance of the database.
         */
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
                        .fallbackToDestructiveMigration()
                        .openHelperFactory(factory)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
