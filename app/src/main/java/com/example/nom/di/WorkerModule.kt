package com.example.nom.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {
    // This module is intentionally left empty for now.
    // It will be used to provide dependencies to Hilt workers.
}
