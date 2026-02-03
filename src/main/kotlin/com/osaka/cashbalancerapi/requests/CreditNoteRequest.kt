package com.osaka.cashbalancerapi.requests

import com.osaka.cashbalancerapi.enums.CreditNoteType
import java.math.BigDecimal

data class CreditNoteRequest(
    val type: CreditNoteType,
    val amount: BigDecimal,
    val notes: String? = null,
)
