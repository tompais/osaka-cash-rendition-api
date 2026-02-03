package com.osaka.cashbalancerapi.services.interfaces
}
    suspend fun deactivatePrice(id: UUID): BigBoxPrice?
     */
     * Desactiva un precio (para actualizar a uno nuevo)
    /**

    suspend fun getAllActivePrices(): List<BigBoxPrice>
     */
     * Obtiene todos los precios activos
    /**

    ): BigBoxPrice?
        date: LocalDateTime = LocalDateTime.now()
        experienceType: BigBoxExperienceType,
    suspend fun getCurrentPrice(
     */
     * Obtiene el precio vigente para un tipo de experiencia en una fecha específica
    /**

    ): BigBoxPrice
        validFrom: LocalDateTime = LocalDateTime.now()
        unitPrice: BigDecimal,
        experienceType: BigBoxExperienceType,
    suspend fun createPrice(
     */
     * Crea un nuevo precio para un tipo de experiencia BigBox
    /**

interface IBigBoxPriceService {

import java.util.UUID
import java.time.LocalDateTime
import java.math.BigDecimal
import com.osaka.cashbalancerapi.entities.BigBoxPrice
import com.osaka.cashbalancerapi.entities.BigBoxExperienceType


