package com.osaka.cashbalancerapi.extensions

import com.osaka.cashbalancerapi.models.Relief
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.ReliefEntity
import java.util.UUID

fun Relief.toEntity(cashRenditionId: UUID) =
    ReliefEntity(
        envelopeNumber = envelopeNumber,
        amount = amount,
        currency = currency,
        exchangeRateSnapshot = exchangeRateSnapshot,
        exchangeRateId = exchangeRateId,
        cashRenditionId = cashRenditionId,
        id = id,
    )

fun ReliefEntity.toDomain() =
    Relief(
        envelopeNumber = envelopeNumber,
        amount = amount,
        currency = currency,
        exchangeRateSnapshot = exchangeRateSnapshot,
        exchangeRateId = exchangeRateId,
        id = id!!,
    )
