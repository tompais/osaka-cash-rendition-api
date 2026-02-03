package com.osaka.cashbalancerapi.requests

import com.osaka.cashbalancerapi.enums.DeliveryPlatform
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class DeliverySaleRequest(
    val platform: DeliveryPlatform,
    @field:Positive(message = "Amount must be greater than zero")
    val amount: BigDecimal,
)
