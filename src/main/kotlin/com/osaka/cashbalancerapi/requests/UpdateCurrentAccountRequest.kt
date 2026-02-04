package com.osaka.cashbalancerapi.requests

import jakarta.validation.constraints.PositiveOrZero
import java.math.BigDecimal

data class UpdateCurrentAccountRequest(
    @field:PositiveOrZero(message = "Current account amount must be positive or zero")
    val amount: BigDecimal,
)
