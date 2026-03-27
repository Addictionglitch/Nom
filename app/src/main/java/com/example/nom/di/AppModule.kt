package com.example.nom.di

import android.content.Context
import com.example.nom.core.data.local.NomDatabase
import com.example.nom.core.data.local.OfflinePlantCache
import com.example.nom.core.data.local.PlantDao
import com.example.nom.core.data.local.ScanHistoryDao
import com.example.nom.core.data.local.SecurePreferences
import com.example.nom.core.data.local.SpiritDao
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides singleton-scoped dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Qualifier for the I/O dispatcher.
     */
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class IoDispatcher

    /**
     * Qualifier for the default dispatcher.
     */
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DefaultDispatcher

    /**
     * Provides the [NomDatabase] instance.
     */
    @Provides
    @Singleton
    fun provideNomDatabase(@ApplicationContext context: Context): NomDatabase {
        // In a real app, the passphrase should be handled more securely
        val passphrase = "your_passphrase".toCharArray()
        return NomDatabase.getInstance(context, passphrase)
    }

    /**
     * Provides the [SecurePreferences] instance.
     */
    @Provides
    @Singleton
    fun provideSecurePreferences(@ApplicationContext context: Context): SecurePreferences {
        return SecurePreferences(context)
    }

    /**
     * Provides the I/O dispatcher.
     */
    @Provides
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    /**
     * Provides the default dispatcher.
     */
    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideSpiritDao(database: NomDatabase): SpiritDao {
        return database.spiritDao()
    }

    @Provides
    @Singleton
    fun providePlantDao(database: NomDatabase): PlantDao {
        return database.plantDao()
    }

    @Provides
    @Singleton
    fun provideScanHistoryDao(database: NomDatabase): ScanHistoryDao {
        return database.scanHistoryDao()
    }

    @Provides
    @Singleton
    fun provideOfflinePlantCache(
        @ApplicationContext context: Context,
        database: NomDatabase
    ): OfflinePlantCache {
        return OfflinePlantCache(context, database)
    }
}
