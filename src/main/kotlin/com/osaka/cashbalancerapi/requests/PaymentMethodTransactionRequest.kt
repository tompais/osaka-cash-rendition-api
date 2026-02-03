package com.osaka.cashbalancerapi.requests

import com.osaka.cashbalancerapi.enums.PaymentMethodType
import java.math.BigDecimal

data class PaymentMethodTransactionRequest(
    val paymentMethodType: PaymentMethodType,
    val amount: BigDecimal,
)
