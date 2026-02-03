package com.osaka.cashbalancerapi.responses

import com.osaka.cashbalancerapi.enums.Currency
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class ExchangeRateResponse(
    val id: UUID,
    val currency: Currency,
    val rateToArs: BigDecimal,
    val validFrom: LocalDate,
    val validUntil: LocalDate?,
)
