package com.osaka.cashbalancerapi.models

import jakarta.validation.Valid

/**
 * Datos adicionales del rendimiento de caja
 * Información cuantitativa (no monetaria) sobre el servicio
 */
data class AdditionalData(
    /** Datos del comedor (dining room) */
    @field:Valid
    val diningRoomData: DiningRoomData = DiningRoomData(),
    /** Datos de delivery Osaka */
    @field:Valid
    val deliveryOsakaData: DeliveryOsakaData = DeliveryOsakaData(),
    /** Datos de delivery Nori Taco */
    @field:Valid
    val deliveryNoriTacoData: DeliveryNoriTacoData = DeliveryNoriTacoData(),
)
