package com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces

import com.osaka.cashbalancerapi.enums.ExperienceType
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.BigBoxEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
interface IBigBoxRepository : CoroutineCrudRepository<BigBoxEntity, UUID> {
    suspend fun findByExperienceTypeAndValidUntilIsNullOrValidUntilAfter(
        experienceType: ExperienceType,
        date: LocalDateTime = LocalDateTime.now(),
    ): BigBoxEntity?
}
