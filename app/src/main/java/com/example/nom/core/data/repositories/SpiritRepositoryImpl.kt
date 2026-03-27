package com.example.nom.core.data.repositories

import com.example.nom.core.data.local.SpiritDao
import com.example.nom.core.data.local.toDomain
import com.example.nom.core.data.local.toEntity
import com.example.nom.core.domain.models.Spirit
import com.example.nom.core.domain.repositories.SpiritRepository
import com.example.nom.core.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override suspend fun createSpirit(spirit: Spirit): Result<Unit> {
        return Result.runCatching {
            spiritDao.insertSpirit(spirit.toEntity())
        }
    }

    override suspend fun updateSpirit(spirit: Spirit): Result<Unit> {
        return Result.runCatching {
            spiritDao.updateSpirit(spirit.toEntity())
        }
    }
}
