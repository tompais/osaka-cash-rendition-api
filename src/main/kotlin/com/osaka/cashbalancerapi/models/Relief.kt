package com.osaka.cashbalancerapi.models

import com.osaka.cashbalancerapi.enums.Currency
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.util.UUID

/**
 * Representa un alivio de caja
 * Un alivio es dinero en efectivo que se guarda en un sobre y se deposita en la caja fuerte
 */
data class Relief(
    @field:NotBlank(message = "Envelope number cannot be blank")
    val envelopeNumber: String,
    @field:Positive(message = "Amount must be greater than zero")
    val amount: BigDecimal,
    val currency: Currency = Currency.ARS,
    @field:Positive(message = "Exchange rate snapshot must be greater than zero")
    val exchangeRateSnapshot: BigDecimal,
    val exchangeRateId: UUID,
    val id: UUID = UUID.randomUUID(),
) {
    init {
        require(envelopeNumber.isNotBlank()) {
            "Envelope number cannot be blank"
        }
        require(amount > BigDecimal.ZERO) {
            "Amount must be greater than 0"
        }
        require(exchangeRateSnapshot > BigDecimal.ZERO) {
            "Exchange rate snapshot must be greater than 0"
        }
    }

    /**
     * Calcula el monto equivalente en ARS
     * Usa el snapshot de la tasa de cambio almacenada
     */
    fun amountInArs(): BigDecimal = amount.multiply(exchangeRateSnapshot)

    companion object {
        fun create(
            envelopeNumber: String,
            amount: BigDecimal,
            currency: Currency = Currency.ARS,
            exchangeRateSnapshot: BigDecimal,
            exchangeRateId: UUID,
        ): Relief =
            Relief(
                envelopeNumber = envelopeNumber,
                amount = amount,
                currency = currency,
                exchangeRateSnapshot = exchangeRateSnapshot,
                exchangeRateId = exchangeRateId,
            )
    }
}
