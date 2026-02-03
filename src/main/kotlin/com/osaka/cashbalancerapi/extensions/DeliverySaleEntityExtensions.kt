package com.osaka.cashbalancerapi.extensions

import com.osaka.cashbalancerapi.models.DeliverySale
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.DeliverySaleEntity
import java.util.UUID

fun DeliverySale.toEntity(cashRenditionId: UUID) =
    DeliverySaleEntity(
        platform = platform,
        amount = amount,
        cashRenditionId = cashRenditionId,
        id = id,
    )

fun DeliverySaleEntity.toDomain() =
    DeliverySale(
        platform = platform,
        amount = amount,
        id = id!!,
    )
