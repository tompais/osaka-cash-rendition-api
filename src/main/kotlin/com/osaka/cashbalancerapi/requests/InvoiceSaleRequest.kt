package com.osaka.cashbalancerapi.requests

import com.osaka.cashbalancerapi.enums.InvoiceType
import java.math.BigDecimal

data class InvoiceSaleRequest(
    val invoiceType: InvoiceType,
    val amount: BigDecimal,
)
