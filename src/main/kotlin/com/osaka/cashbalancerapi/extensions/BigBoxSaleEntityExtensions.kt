package com.osaka.cashbalancerapi.extensions

import com.osaka.cashbalancerapi.models.BigBoxSale
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.BigBoxSaleEntity
import java.util.UUID

fun BigBoxSale.toEntity(cashRenditionId: UUID) =
    BigBoxSaleEntity(
        bigBoxId = bigBoxId,
        quantity = quantity,
        unitPriceSnapshot = unitPriceSnapshot,
        cashRenditionId = cashRenditionId,
        id = id,
    )

fun BigBoxSaleEntity.toDomain() =
    BigBoxSale(
        quantity = quantity,
        unitPriceSnapshot = unitPriceSnapshot,
        bigBoxId = bigBoxId,
        id = id!!,
    )
