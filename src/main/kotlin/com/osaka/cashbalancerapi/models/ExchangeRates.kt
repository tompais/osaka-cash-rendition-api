package com.osaka.cashbalancerapi.models

import java.math.BigDecimal

/**
 * Tasas de cambio específicas de un rendimiento de caja
 * Cada rendimiento captura las tasas de cambio del momento en que se crea
 * Para ARS no hay atributo porque la tasa siempre es 1
 */
data class ExchangeRates(
    /** Tasa de cambio de USD a ARS */
    val usdToArs: BigDecimal = BigDecimal.ONE,
    /** Tasa de cambio de BRL a ARS */
    val brlToArs: BigDecimal = BigDecimal.ONE,
    /** Tasa de cambio de EUR a ARS */
    val eurToArs: BigDecimal = BigDecimal.ONE,
)
