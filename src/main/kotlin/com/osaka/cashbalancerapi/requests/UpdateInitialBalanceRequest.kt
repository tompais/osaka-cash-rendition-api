package com.osaka.cashbalancerapi.requests

import jakarta.validation.constraints.PositiveOrZero
import java.math.BigDecimal

data class UpdateInitialBalanceRequest(
    @field:PositiveOrZero(message = "Initial balance must be positive or zero")
    val amount: BigDecimal,
)
