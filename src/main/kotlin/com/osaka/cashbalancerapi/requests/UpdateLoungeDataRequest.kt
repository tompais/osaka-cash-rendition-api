package com.osaka.cashbalancerapi.requests

import jakarta.validation.constraints.PositiveOrZero

data class UpdateLoungeDataRequest(
    @field:PositiveOrZero(message = "Otoshis must be positive or zero")
    val otoshis: UInt,
)
