package com.osaka.cashbalancerapi.services.implementations

import com.osaka.cashbalancerapi.enums.ExperienceType
import com.osaka.cashbalancerapi.extensions.toDomain
import com.osaka.cashbalancerapi.extensions.toEntity
import com.osaka.cashbalancerapi.models.BigBox
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.BigBoxEntity
import com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces.IBigBoxRepository
import com.osaka.cashbalancerapi.services.interfaces.IBigBoxService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Service
class BigBoxService(
    private val bigBoxRepository: IBigBoxRepository,
) : IBigBoxService {
    override suspend fun create(
        experienceType: ExperienceType,
        unitPrice: BigDecimal,
    ): BigBox = saveBigBoxEntity(createBigBox(experienceType, unitPrice).toEntity()).toDomain()

    private suspend fun saveBigBoxEntity(entity: BigBoxEntity): BigBoxEntity = bigBoxRepository.save(entity)

    private fun createBigBox(
        experienceType: ExperienceType,
        unitPrice: BigDecimal,
    ): BigBox = BigBox.create(experienceType, unitPrice)

    override suspend fun getActiveByExperienceType(experienceType: ExperienceType): BigBox? =
        bigBoxRepository
            .findByExperienceTypeAndValidUntilIsNullOrValidUntilAfter(
                experienceType,
            )?.toDomain()

    override fun getAll(): Flow<BigBox> =
        bigBoxRepository
            .findAll()
            .map(BigBoxEntity::toDomain)

    override suspend fun getById(id: UUID): BigBox? =
        findBigBoxEntityById(id)
            ?.toDomain()

    override suspend fun updateValidUntil(
        id: UUID,
        validUntil: LocalDateTime,
    ): BigBox? =
        findBigBoxEntityById(id)
            ?.copy(validUntil = validUntil)
            ?.let { bigBoxEntity -> saveBigBoxEntity(bigBoxEntity) }
            ?.toDomain()

    private suspend fun findBigBoxEntityById(id: UUID): BigBoxEntity? = bigBoxRepository.findById(id)
}
