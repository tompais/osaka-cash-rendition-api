package com.osaka.cashbalancerapi.requests

import com.osaka.cashbalancerapi.enums.Currency
import java.math.BigDecimal

data class ReliefRequest(
    val envelopeNumber: String,
    val currency: Currency,
    val amount: BigDecimal,
)
