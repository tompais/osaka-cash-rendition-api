package com.osaka.cashbalancerapi.entities

import java.math.BigDecimal

/**
 * Representa una venta con factura
 */
data class InvoiceSale(
    val invoiceType: InvoiceType,
    val amount: BigDecimal,
)

enum class InvoiceType {
    /** Factura A - Para empresas */
    A,

    /** Factura B - Para consumidor final */
    B,
}
