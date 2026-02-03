package com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces

import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.ReliefEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface IReliefRepository : CoroutineCrudRepository<ReliefEntity, UUID> {
    suspend fun findAllByCashRenditionId(cashRenditionId: UUID): List<ReliefEntity>

    suspend fun findByEnvelopeNumberAndCashRenditionId(
        envelopeNumber: String,
        cashRenditionId: UUID,
    ): ReliefEntity?
}
