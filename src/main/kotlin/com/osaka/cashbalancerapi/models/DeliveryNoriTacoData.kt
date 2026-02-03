package com.osaka.cashbalancerapi.models

/**
 * Datos adicionales de delivery Nori Taco
 * Información sobre pedidos y comensales
 */
data class DeliveryNoriTacoData(
    /** Cantidad de pedidos de Nori Taco */
    val orders: UInt = 0u,
    /** Cantidad de comensales (ohashis) en los pedidos */
    val ohashis: UInt = 0u,
)
