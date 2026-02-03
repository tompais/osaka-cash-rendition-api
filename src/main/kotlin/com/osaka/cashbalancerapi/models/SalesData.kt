package com.osaka.cashbalancerapi.entities

import java.math.BigDecimal

/**
 * Datos de venta del rendimiento de caja
 * Contiene todos los tipos de ventas registradas durante el turno
 */
data class SalesData(
    /** Ventas con facturas (A y B) - Máximo 2 elementos */
    val invoiceSales: List<InvoiceSale> = emptyList(),
    /** Ventas por plataformas de delivery */
    val deliverySales: List<DeliverySale> = emptyList(),
    /** Ventas de vouchers BigBox */
    val bigBoxSales: List<BigBoxSale> = emptyList(),
    /** Saldo inicial de caja */
    val initialBalance: BigDecimal = BigDecimal.ZERO,
    /** Ventas en negro (sin factura) - Marketing */
    val marketing: BigDecimal = BigDecimal.ZERO,
    /** Ventas en cuenta corriente */
    val currentAccount: BigDecimal = BigDecimal.ZERO,
) {
    /**
     * Calcula el total de ventas con facturas
     */
    fun totalInvoiceSales(): BigDecimal =
        invoiceSales
            .map { it.amount }
            .fold(BigDecimal.ZERO) { acc, amount -> acc.add(amount) }

    /**
     * Calcula el total de ventas por delivery
     */
    fun totalDeliverySales(): BigDecimal =
        deliverySales
            .map { it.amount }
            .fold(BigDecimal.ZERO) { acc, amount -> acc.add(amount) }

    /**
     * Calcula el total de ventas BigBox
     */
    fun totalBigBoxSales(): BigDecimal =
        bigBoxSales
            .map { it.totalAmount() }
            .fold(BigDecimal.ZERO) { acc, amount -> acc.add(amount) }

    /**
     * Calcula el total general de ventas
     */
    fun totalSales(): BigDecimal =
        initialBalance
            .add(marketing)
            .add(currentAccount)
            .add(totalInvoiceSales())
            .add(totalDeliverySales())
            .add(totalBigBoxSales())
}
