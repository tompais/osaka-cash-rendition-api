package com.osaka.cashbalancerapi.models

/**
 * Datos de pedidos de delivery de Nori Taco
 * Información cuantitativa sobre pedidos (no monetaria)
 */
data class NoriTacoOrdersData(
    /** Cantidad de pedidos realizados */
    val orders: UInt = 0u,
    /** Cantidad de comensales (ohashis) en los pedidos */
    val ohashis: UInt = 0u,
)
