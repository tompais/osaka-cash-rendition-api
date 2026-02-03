package com.osaka.cashbalancerapi.entities

import org.springframework.data.mongodb.core.mapping.DBRef
import java.math.BigDecimal
import java.util.UUID

/**
 * Representa una venta de vouchers BigBox en un rendimiento
 * Almacena la cantidad vendida y una referencia al precio configurado
 */
data class BigBoxSale(
    val experienceType: BigBoxExperienceType,
    val quantity: UInt,
    val unitPriceSnapshot: BigDecimal, // Snapshot del precio para histórico
    @DBRef
    val bigBoxPriceId: UUID, // Referencia al precio vigente al momento de la venta
) {
    init {
        require(quantity > 0u) {
            "Quantity must be greater than 0"
        }
    }

    /**
     * Calcula el total recaudado
     */
    fun totalAmount(): BigDecimal = unitPriceSnapshot.multiply(BigDecimal(quantity.toLong()))
}

enum class BigBoxExperienceType {
    GRAND_CUISINE,
    PREMIADOS,
    XPS_BENTO_BOX,
    XS_TO_GO,
}
