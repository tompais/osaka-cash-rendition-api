package com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces

import com.osaka.cashbalancerapi.enums.InvoiceType
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.InvoiceSaleEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface IInvoiceSaleRepository : CoroutineCrudRepository<InvoiceSaleEntity, UUID> {
    fun findAllByCashRenditionId(cashRenditionId: UUID): Flow<InvoiceSaleEntity>

    suspend fun findByTypeAndCashRenditionId(
        type: InvoiceType,
        cashRenditionId: UUID,
    ): InvoiceSaleEntity?
}
