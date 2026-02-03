package com.osaka.cashbalancerapi.models

import com.osaka.cashbalancerapi.enums.Currency
import java.math.BigDecimal
import java.util.UUID

/**
 * Representa un alivio de caja
 * Un alivio es dinero en efectivo que se guarda en un sobre y se deposita en la caja fuerte
 */
data class Relief(
    val reliefNumber: UInt, // Número autoincremental del alivio (1, 2, 3, ...)
    val envelopeNumber: String, // Número del sobre (ingresado manualmente, único)
    val currency: Currency, // Tipo de moneda
    val amount: BigDecimal, // Monto del alivio
    val id: UUID = UUID.randomUUID(),
) {
    init {
        require(reliefNumber > 0u) {
            "Relief number must be greater than 0"
        }
        require(envelopeNumber.isNotBlank()) {
            "Envelope number cannot be blank"
        }
        require(amount > BigDecimal.ZERO) {
            "Amount must be greater than 0"
        }
    }

    companion object {
        fun create(
            reliefNumber: UInt,
            envelopeNumber: String,
            currency: Currency,
            amount: BigDecimal,
        ): Relief =
            Relief(
                reliefNumber = reliefNumber,
                envelopeNumber = envelopeNumber,
                currency = currency,
                amount = amount,
            )
    }
}
