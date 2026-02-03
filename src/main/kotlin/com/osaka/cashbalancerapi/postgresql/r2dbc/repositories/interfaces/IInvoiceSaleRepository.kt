package com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces

import com.osaka.cashbalancerapi.enums.InvoiceType
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.InvoiceSaleEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface IInvoiceSaleRepository : CoroutineCrudRepository<InvoiceSaleEntity, UUID> {
    suspend fun findAllByCashRenditionId(cashRenditionId: UUID): List<InvoiceSaleEntity>

    suspend fun findByTypeAndCashRenditionId(
        type: InvoiceType,
        cashRenditionId: UUID,
    ): InvoiceSaleEntity?
}
