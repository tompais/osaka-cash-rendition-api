package com.osaka.cashbalancerapi.models

import com.osaka.cashbalancerapi.enums.PaymentMethodType
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.util.UUID

/**
 * Transacción de Medio de Pago
 * Representa el total cobrado por un medio de pago específico en un rendimiento de caja
 */
data class PaymentMethodTransaction(
    val paymentMethodType: PaymentMethodType,
    @field:Positive(message = "Amount must be greater than zero")
    val amount: BigDecimal,
    val id: UUID = UUID.randomUUID(),
)
