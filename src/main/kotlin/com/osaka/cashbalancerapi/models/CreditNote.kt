package com.osaka.cashbalancerapi.models

import com.osaka.cashbalancerapi.enums.CreditNoteType
import java.math.BigDecimal
import java.util.UUID

/**
 * Representa una nota de crédito que anula parte de las ventas
 * Las notas de crédito reducen el rendimiento total de caja
 */
data class CreditNote(
    val type: CreditNoteType,
    val amount: BigDecimal,
    val notes: String? = null,
    val id: UUID = UUID.randomUUID(),
)
