package com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces

import com.osaka.cashbalancerapi.enums.PaymentMethodType
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.PaymentMethodTransactionEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface IPaymentMethodTransactionRepository : CoroutineCrudRepository<PaymentMethodTransactionEntity, UUID> {
    fun findAllByCashRenditionId(cashRenditionId: UUID): Flow<PaymentMethodTransactionEntity>

    suspend fun findByPaymentMethodTypeAndCashRenditionId(
        paymentMethodType: PaymentMethodType,
        cashRenditionId: UUID,
    ): PaymentMethodTransactionEntity?
}
