package com.osaka.cashbalancerapi.r2dbc.repositories.interfaces

import com.osaka.cashbalancerapi.entities.BigBoxEntity
import com.osaka.cashbalancerapi.models.BigBox
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
interface IBigBoxRepository : CoroutineCrudRepository<BigBoxEntity, UUID> {
    fun findByExperienceType(experienceType: BigBox.ExperienceType): Flow<BigBoxEntity>

    suspend fun findByExperienceTypeAndValidUntilIsNullOrValidUntilAfter(
        experienceType: BigBox.ExperienceType,
        date: LocalDateTime,
    ): BigBoxEntity?
}
