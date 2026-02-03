package com.osaka.cashbalancerapi.requests

import jakarta.validation.constraints.FutureOrPresent
import java.time.LocalDate

data class EndExchangeRateRequest(
    @field:FutureOrPresent(message = "Valid until date cannot be in the past")
    val validUntil: LocalDate,
)
