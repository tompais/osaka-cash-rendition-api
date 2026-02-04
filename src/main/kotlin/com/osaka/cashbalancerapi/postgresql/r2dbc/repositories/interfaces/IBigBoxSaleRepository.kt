package com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces

import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.BigBoxSaleEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface IBigBoxSaleRepository : CoroutineCrudRepository<BigBoxSaleEntity, UUID> {
    fun findAllByCashRenditionId(cashRenditionId: UUID): Flow<BigBoxSaleEntity>
}
