package com.osaka.cashbalancerapi.models

import java.math.BigDecimal
import java.util.UUID

/**
 * Representa una venta de vouchers BigBox en un rendimiento
 * Almacena la cantidad vendida y una referencia al precio configurado
 */
data class BigBoxSale(
    val quantity: UInt,
    val unitPriceSnapshot: BigDecimal,
    val bigBoxId: UUID,
    val id: UUID = UUID.randomUUID(),
) {
    /**
     * Calcula el total recaudado
     */
    fun totalAmount(): BigDecimal = unitPriceSnapshot.multiply(BigDecimal(quantity.toLong()))
}
