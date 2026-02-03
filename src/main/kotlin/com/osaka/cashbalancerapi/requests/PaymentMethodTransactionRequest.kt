package com.osaka.cashbalancerapi.requests

import com.osaka.cashbalancerapi.enums.PaymentMethodType
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class PaymentMethodTransactionRequest(
    val paymentMethodType: PaymentMethodType,
    @field:Positive(message = "Amount must be greater than zero")
    val amount: BigDecimal,
)
