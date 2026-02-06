package com.osaka.cashbalancerapi.models

/**
 * Datos de pedidos de delivery de Osaka
 * Información cuantitativa sobre pedidos (no monetaria)
 */
data class OsakaOrdersData(
    /** Cantidad de comensales (ohashis) en los pedidos */
    val ohashis: UInt = 0u,
    /** Cantidad de pedidos realizados */
    val orders: UInt = 0u,
)
