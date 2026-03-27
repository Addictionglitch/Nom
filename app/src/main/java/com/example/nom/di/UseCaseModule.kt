package com.example.nom.di

import com.example.nom.core.domain.repositories.PlantRepository
import com.example.nom.core.domain.repositories.SpiritRepository
import com.example.nom.core.domain.usecases.DecaySpiritStatsUseCase
import com.example.nom.core.domain.usecases.EvolveSpiritUseCase
import com.example.nom.core.domain.usecases.FeedSpiritUseCase
import com.example.nom.core.domain.usecases.GetSpiritStateUseCase
import com.example.nom.core.domain.usecases.ScanPlantUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetSpiritStateUseCase(spiritRepository: SpiritRepository): GetSpiritStateUseCase {
        return GetSpiritStateUseCase(spiritRepository)
    }

    @Provides
    fun provideFeedSpiritUseCase(spiritRepository: SpiritRepository): FeedSpiritUseCase {
        return FeedSpiritUseCase(spiritRepository)
    }

    @Provides
    fun provideEvolveSpiritUseCase(): EvolveSpiritUseCase {
        return EvolveSpiritUseCase()
    }

    @Provides
    fun provideDecaySpiritStatsUseCase(spiritRepository: SpiritRepository): DecaySpiritStatsUseCase {
        return DecaySpiritStatsUseCase(spiritRepository)
    }

    @Provides
    fun provideScanPlantUseCase(
        plantRepository: PlantRepository,
        spiritRepository: SpiritRepository,
        feedSpiritUseCase: FeedSpiritUseCase,
        evolveSpiritUseCase: EvolveSpiritUseCase
    ): ScanPlantUseCase {
        return ScanPlantUseCase(
            plantRepository,
            spiritRepository,
            feedSpiritUseCase,
            evolveSpiritUseCase
        )
    }
}
