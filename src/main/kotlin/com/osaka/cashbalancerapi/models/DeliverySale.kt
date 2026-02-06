package com.osaka.cashbalancerapi.models

import com.osaka.cashbalancerapi.enums.DeliveryPlatform
import java.math.BigDecimal
import java.util.UUID

/**
 * Representa una venta realizada a través de plataformas de delivery
 */
data class DeliverySale(
    val platform: DeliveryPlatform,
    val amount: BigDecimal,
    val id: UUID = UUID.randomUUID(),
)
