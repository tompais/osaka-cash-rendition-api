package com.osaka.cashbalancerapi.requests

import com.osaka.cashbalancerapi.enums.InvoiceType
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class InvoiceSaleRequest(
    val invoiceType: InvoiceType,
    @field:Positive(message = "Amount must be greater than zero")
    val amount: BigDecimal,
)
