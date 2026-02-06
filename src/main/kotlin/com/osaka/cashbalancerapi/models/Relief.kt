package com.osaka.cashbalancerapi.models

import com.osaka.cashbalancerapi.enums.Currency
import java.math.BigDecimal
import java.util.UUID

/**
 * Representa un alivio de caja
 * Un alivio es dinero en efectivo que se guarda en un sobre y se deposita en la caja fuerte
 */
data class Relief(
    val envelopeNumber: String,
    val amount: BigDecimal,
    val currency: Currency = Currency.ARS,
    val exchangeRateSnapshot: BigDecimal,
    val exchangeRateId: UUID,
    val id: UUID = UUID.randomUUID(),
) {
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
