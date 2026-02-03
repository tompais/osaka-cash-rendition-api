package com.osaka.cashbalancerapi.models

import com.osaka.cashbalancerapi.enums.CreditNoteType
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.util.UUID

/**
 * Representa una nota de crédito que anula parte de las ventas
 * Las notas de crédito reducen el rendimiento total de caja
 */
data class CreditNote(
    val type: CreditNoteType,
    @field:Positive(message = "Amount must be greater than zero")
    val amount: BigDecimal,
    @field:Size(max = 500, message = "Notes cannot exceed 500 characters")
    val notes: String? = null,
    val id: UUID = UUID.randomUUID(),
)
