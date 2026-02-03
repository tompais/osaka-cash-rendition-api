package com.osaka.cashbalancerapi.models

import com.osaka.cashbalancerapi.enums.InvoiceType
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.util.UUID

/**
 * Representa una venta con factura (A o B)
 */
data class InvoiceSale(
    val type: InvoiceType,
    @field:Positive(message = "Amount must be greater than zero")
    val amount: BigDecimal,
    val id: UUID = UUID.randomUUID(),
)
