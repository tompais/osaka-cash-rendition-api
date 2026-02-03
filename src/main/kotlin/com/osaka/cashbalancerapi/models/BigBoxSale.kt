package com.osaka.cashbalancerapi.models

import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.util.UUID

/**
 * Representa una venta de vouchers BigBox en un rendimiento
 * Almacena la cantidad vendida y una referencia al precio configurado
 */
data class BigBoxSale(
    @field:Positive(message = "Quantity must be greater than 0")
    val quantity: UInt,
    @field:Positive(message = "Unit price snapshot must be greater than zero")
    val unitPriceSnapshot: BigDecimal,
    val bigBoxId: UUID,
    val id: UUID = UUID.randomUUID(),
) {
    /**
     * Calcula el total recaudado
     */
    fun totalAmount(): BigDecimal = unitPriceSnapshot.multiply(BigDecimal(quantity.toLong()))
}
