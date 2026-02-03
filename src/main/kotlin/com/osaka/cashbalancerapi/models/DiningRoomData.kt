package com.osaka.cashbalancerapi.models

/**
 * Datos adicionales del salón (comedor)
 * Información sobre la cantidad de personas que comieron en el restaurante
 */
data class DiningRoomData(
    /** Cantidad de cubiertos/personas que comieron en el salón */
    val otoshis: UInt = 0u,
)
