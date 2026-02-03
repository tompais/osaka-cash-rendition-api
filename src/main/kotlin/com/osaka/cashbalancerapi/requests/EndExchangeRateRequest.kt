package com.osaka.cashbalancerapi.requests

import java.time.LocalDate

data class EndExchangeRateRequest(
    val validUntil: LocalDate,
)
