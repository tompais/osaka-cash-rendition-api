package com.osaka.cashbalancerapi.models

/**
 * Datos adicionales del lounge (salón/comedor)
 * Información sobre la cantidad de personas que comieron en el restaurante
 */
data class LoungeData(
    /** Cantidad de cubiertos/personas que comieron en el lounge */
    val otoshis: UInt = 0u,
    /** Cantidad de comensales (ohashis) en el lounge */
    val ohashis: UInt = 0u,
)
