package com.osaka.cashbalancerapi.models

import com.osaka.cashbalancerapi.enums.Currency
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

/**
 * Representa el tipo de cambio de una moneda extranjera a pesos argentinos
 */
data class ExchangeRate(
    val currency: Currency,
    val rateToArs: BigDecimal,
    val validFrom: LocalDateTime,
    val validUntil: LocalDateTime? = null,
    val id: UUID = UUID.randomUUID(),
) {
    /**
     * Verifica si este tipo de cambio está activo en una fecha determinada
     */
    fun isActiveAt(dateTime: LocalDateTime): Boolean =
        dateTime.isAfter(validFrom) &&
            (validUntil == null || dateTime.isBefore(validUntil))
}
