package com.osaka.cashbalancerapi.requests

import com.osaka.cashbalancerapi.enums.CreditNoteType
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class CreditNoteRequest(
    val type: CreditNoteType,
    @field:Positive(message = "Amount must be greater than zero")
    val amount: BigDecimal,
    @field:Size(max = 500, message = "Notes cannot exceed 500 characters")
    val notes: String? = null,
)
