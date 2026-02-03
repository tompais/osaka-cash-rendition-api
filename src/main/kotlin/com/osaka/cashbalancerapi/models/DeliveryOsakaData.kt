package com.osaka.cashbalancerapi.models

/**
 * Datos adicionales de delivery Osaka
 * Información sobre pedidos y comensales
 */
data class DeliveryOsakaData(
    /** Cantidad de comensales (ohashis) en los pedidos */
    val ohashis: UInt = 0u,
    /** Cantidad total de pedidos */
    val orders: UInt = 0u,
)
