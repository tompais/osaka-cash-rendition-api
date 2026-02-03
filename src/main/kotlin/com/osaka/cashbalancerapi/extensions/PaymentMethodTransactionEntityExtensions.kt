package com.osaka.cashbalancerapi.extensions

import com.osaka.cashbalancerapi.models.PaymentMethodTransaction
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.PaymentMethodTransactionEntity
import java.util.UUID

fun PaymentMethodTransaction.toEntity(cashRenditionId: UUID) =
    PaymentMethodTransactionEntity(
        paymentMethodType = paymentMethodType,
        amount = amount,
        cashRenditionId = cashRenditionId,
        id = id,
    )

fun PaymentMethodTransactionEntity.toDomain() =
    PaymentMethodTransaction(
        paymentMethodType = paymentMethodType,
        amount = amount,
        id = id!!,
    )
