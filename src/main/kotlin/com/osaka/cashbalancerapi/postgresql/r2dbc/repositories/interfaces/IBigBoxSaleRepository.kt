package com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces

import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.BigBoxSaleEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface IBigBoxSaleRepository : CoroutineCrudRepository<BigBoxSaleEntity, UUID> {
    suspend fun findAllByCashRenditionId(cashRenditionId: UUID): List<BigBoxSaleEntity>
}
