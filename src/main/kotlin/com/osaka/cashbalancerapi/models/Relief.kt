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
    val id: UUID = UUID.randomUUID(),
)
