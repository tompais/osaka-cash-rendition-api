package com.osaka.cashbalancerapi.requests

import jakarta.validation.constraints.PositiveOrZero

data class UpdateNoriTacoOrdersRequest(
    @field:PositiveOrZero(message = "Orders must be positive or zero")
    val orders: UInt,
    @field:PositiveOrZero(message = "Ohashis must be positive or zero")
    val ohashis: UInt,
)
