package com.osaka.cashbalancerapi.models

import com.osaka.cashbalancerapi.enums.ExperienceType
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

/**
 * Configuración de precio para un tipo de experiencia BigBox
 * Almacena el precio unitario de cada tipo de voucher
 */
data class BigBox(
    val experienceType: ExperienceType,
    @field:Positive(message = "Unit price must be greater than zero")
    val unitPrice: BigDecimal,
    val validFrom: LocalDateTime,
    val validUntil: LocalDateTime? = null,
    val id: UUID = UUID.randomUUID(),
) {
    companion object {
        fun create(
            experienceType: ExperienceType,
            unitPrice: BigDecimal,
            validFrom: LocalDateTime = LocalDateTime.now(),
        ): BigBox =
            BigBox(
                experienceType = experienceType,
                unitPrice = unitPrice,
                validFrom = validFrom,
            )
    }
}
