package com.osaka.cashbalancerapi.requests

import com.osaka.cashbalancerapi.enums.Currency
import jakarta.validation.constraints.PastOrPresent
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.time.LocalDate

data class CreateExchangeRateRequest(
    val currency: Currency,
    @field:Positive(message = "Rate to ARS must be greater than zero")
    val rateToArs: BigDecimal,
    @field:PastOrPresent(message = "Valid from date cannot be in the future")
    val validFrom: LocalDate = LocalDate.now(),
)
