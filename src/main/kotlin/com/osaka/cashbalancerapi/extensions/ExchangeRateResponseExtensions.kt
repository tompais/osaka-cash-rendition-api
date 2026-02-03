package com.osaka.cashbalancerapi.extensions

import com.osaka.cashbalancerapi.models.ExchangeRate
import com.osaka.cashbalancerapi.responses.ExchangeRateResponse

fun ExchangeRate.toResponse() =
    ExchangeRateResponse(
        id = id,
        currency = currency,
        rateToArs = rateToArs,
        validFrom = validFrom.toLocalDate(),
        validUntil = validUntil?.toLocalDate(),
    )
