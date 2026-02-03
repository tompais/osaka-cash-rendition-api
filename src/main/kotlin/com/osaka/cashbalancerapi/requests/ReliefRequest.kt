package com.osaka.cashbalancerapi.requests

import com.osaka.cashbalancerapi.enums.Currency
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class ReliefRequest(
    @field:NotBlank(message = "Envelope number cannot be blank")
    val envelopeNumber: String,
    val currency: Currency,
    @field:Positive(message = "Amount must be greater than zero")
    val amount: BigDecimal,
)
