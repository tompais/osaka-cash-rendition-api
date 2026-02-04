package com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces

import com.osaka.cashbalancerapi.enums.DeliveryPlatform
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.DeliverySaleEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface IDeliverySaleRepository : CoroutineCrudRepository<DeliverySaleEntity, UUID> {
    fun findAllByCashRenditionId(cashRenditionId: UUID): Flow<DeliverySaleEntity>

    suspend fun findByPlatformAndCashRenditionId(
        platform: DeliveryPlatform,
        cashRenditionId: UUID,
    ): DeliverySaleEntity?
}
