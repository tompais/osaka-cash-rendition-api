package com.osaka.cashbalancerapi.models

import jakarta.validation.Valid
import jakarta.validation.constraints.PositiveOrZero
import java.math.BigDecimal

/**
 * Datos de venta del rendimiento de caja
 * Contiene todos los tipos de ventas registradas durante el turno
 */
data class SalesData(
    /** Ventas con facturas (A y B) - Máximo 2 elementos */
    @field:Valid
    val invoiceSales: List<InvoiceSale> = emptyList(),
    /** Ventas por plataformas de delivery */
    @field:Valid
    val deliverySales: List<DeliverySale> = emptyList(),
    /** Ventas de vouchers BigBox */
    @field:Valid
    val bigBoxSales: List<BigBoxSale> = emptyList(),
    /** Notas de crédito (anulaciones de ventas) */
    @field:Valid
    val creditNotes: List<CreditNote> = emptyList(),
    /** Saldo inicial de caja */
    @field:PositiveOrZero(message = "Initial balance must be greater than or equal to zero")
    val initialBalance: BigDecimal = BigDecimal.ZERO,
    /** Ventas en negro (sin factura) - Marketing */
    @field:PositiveOrZero(message = "Marketing amount must be greater than or equal to zero")
    val marketing: BigDecimal = BigDecimal.ZERO,
    /** Ventas en cuenta corriente */
    @field:PositiveOrZero(message = "Current account amount must be greater than or equal to zero")
    val currentAccount: BigDecimal = BigDecimal.ZERO,
) {
    /**
     * Calcula el total de ventas con facturas
     */
    fun totalInvoiceSales(): BigDecimal =
        invoiceSales
            .map(InvoiceSale::amount)
            .fold(BigDecimal.ZERO) { acc, amount -> acc.add(amount) }

    /**
     * Calcula el total de ventas por delivery
     */
    fun totalDeliverySales(): BigDecimal =
        deliverySales
            .map(DeliverySale::amount)
            .fold(BigDecimal.ZERO) { acc, amount -> acc.add(amount) }

    /**
     * Calcula el total de ventas BigBox
     */
    fun totalBigBoxSales(): BigDecimal =
        bigBoxSales
            .map(BigBoxSale::totalAmount)
            .fold(BigDecimal.ZERO) { acc, amount -> acc.add(amount) }

    /**
     * Calcula el total de notas de crédito (anulaciones)
     */
    fun totalCreditNotes(): BigDecimal =
        creditNotes
            .map(CreditNote::amount)
            .fold(BigDecimal.ZERO) { acc, amount -> acc.add(amount) }

    /**
     * Calcula el total de ventas brutas (sin descontar notas de crédito ni incluir saldo inicial)
     */
    fun totalGrossSales(): BigDecimal =
        marketing
            .add(currentAccount)
            .add(totalInvoiceSales())
            .add(totalDeliverySales())
            .add(totalBigBoxSales())

    /**
     * Calcula el total de ventas netas (descontando las notas de crédito)
     */
    fun totalNetSales(): BigDecimal = totalGrossSales().subtract(totalCreditNotes())

    /**
     * Calcula el total disponible en caja (incluyendo saldo inicial y ventas netas)
     */
    fun totalAvailable(): BigDecimal = initialBalance.add(totalNetSales())
}
