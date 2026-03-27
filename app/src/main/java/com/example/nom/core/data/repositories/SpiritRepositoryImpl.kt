package com.example.nom.core.data.repositories

import com.example.nom.core.data.local.SpiritDao
import com.example.nom.core.data.local.toDomain
import com.example.nom.core.data.local.toEntity
import com.example.nom.core.domain.models.Spirit
import com.example.nom.core.domain.repositories.SpiritRepository
import com.example.nom.core.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

/**
 * Implementation of the SpiritRepository.
 *
 * @param spiritDao The DAO for accessing Spirit data.
 */
class SpiritRepositoryImpl @Inject constructor(
    private val spiritDao: SpiritDao
) : SpiritRepository {

    override fun observeSpirit(): Flow<Spirit?> {
        return spiritDao.getSpirit().map { it?.toDomain() }
    }

    override suspend fun createSpirit(name: String): Result<Spirit> {
        return Result.runCatching {
            val newSpirit = Spirit(
                id = UUID.randomUUID().toString(),
                name = name,
            )
            spiritDao.insertSpirit(newSpirit.toEntity())
            newSpirit
        }
    }

    override suspend fun updateSpirit(spirit: Spirit): Result<Unit> {
        return Result.runCatching {
            spiritDao.updateSpirit(spirit.toEntity())
        }
    }

    override suspend fun applyGardenRewards(): Result<Unit> {
        return Result.runCatching {
            val currentSpirit = observeSpirit().first()
            if (currentSpirit != null) {
                val updatedSpirit = currentSpirit.copy(
                    happiness = (currentSpirit.happiness + 0.1f).coerceAtMost(1.0f),
                    energy = (currentSpirit.energy + 0.1f).coerceAtMost(1.0f)
                )
                updateSpirit(updatedSpirit)
            }
        }
    }
}
