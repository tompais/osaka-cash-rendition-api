package com.osaka.cashbalancerapi.extensions

import com.osaka.cashbalancerapi.models.ExchangeRate
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.ExchangeRateEntity

fun ExchangeRate.toEntity() =
    ExchangeRateEntity(
        currency = currency,
        rateToArs = rateToArs,
        validFrom = validFrom,
        validUntil = validUntil,
        id = id,
    )

fun ExchangeRateEntity.toDomain() =
    ExchangeRate(
        currency = currency,
        rateToArs = rateToArs,
        validFrom = validFrom,
        validUntil = validUntil,
        id = id!!,
    )
