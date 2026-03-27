package com.example.nom.di

import com.example.nom.core.data.repositories.PlantRepositoryImpl
import com.example.nom.core.data.repositories.SpiritRepositoryImpl
import com.example.nom.core.domain.repositories.PlantRepository
import com.example.nom.core.domain.repositories.SpiritRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSpiritRepository(impl: SpiritRepositoryImpl): SpiritRepository

    @Binds
    abstract fun bindPlantRepository(impl: PlantRepositoryImpl): PlantRepository
}
