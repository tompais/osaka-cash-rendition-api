package com.osaka.cashbalancerapi.entities

import java.math.BigDecimal

/**
 * Representa una venta por delivery (plataforma de entrega)
 */
data class DeliverySale(
    val platform: DeliveryPlatform,
    val amount: BigDecimal,
)

enum class DeliveryPlatform {
    RAPPI,
    PEDIDOS_YA,
    NORI_TACO,
}
