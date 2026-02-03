package com.osaka.cashbalancerapi.entities

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

/**
 * Configuración de precio para un tipo de experiencia BigBox
 * Almacena el precio unitario de cada tipo de voucher
 */
data class BigBoxPrice(
    val experienceType: BigBoxExperienceType,
    val unitPrice: BigDecimal,
    val validFrom: LocalDateTime,
    val validUntil: LocalDateTime? = null, // null = vigente indefinidamente
    val id: UUID = UUID.randomUUID(),
) {
    companion object {
        fun create(
            experienceType: BigBoxExperienceType,
            unitPrice: BigDecimal,
            validFrom: LocalDateTime = LocalDateTime.now(),
        ): BigBoxPrice =
            BigBoxPrice(
                experienceType = experienceType,
                unitPrice = unitPrice,
                validFrom = validFrom,
            )
    }
}
