package com.osaka.cashbalancerapi.extensions

import com.osaka.cashbalancerapi.models.InvoiceSale
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.InvoiceSaleEntity
import java.util.UUID

fun InvoiceSale.toEntity(cashRenditionId: UUID) =
    InvoiceSaleEntity(
        type = type,
        amount = amount,
        cashRenditionId = cashRenditionId,
        id = id,
    )

fun InvoiceSaleEntity.toDomain() =
    InvoiceSale(
        type = type,
        amount = amount,
        id = id!!,
    )
