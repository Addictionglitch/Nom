package com.example.nom.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    // @Binds abstract fun bindSpiritRepository(impl: SpiritRepositoryImpl): SpiritRepository
    // ... and so on for the other repositories
}
