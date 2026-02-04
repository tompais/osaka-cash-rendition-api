package com.osaka.cashbalancerapi.requests

import jakarta.validation.constraints.PositiveOrZero
import java.math.BigDecimal

data class UpdateMarketingRequest(
    @field:PositiveOrZero(message = "Marketing amount must be positive or zero")
    val amount: BigDecimal,
)
