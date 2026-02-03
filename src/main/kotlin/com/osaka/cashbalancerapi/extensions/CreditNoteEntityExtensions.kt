package com.osaka.cashbalancerapi.extensions

import com.osaka.cashbalancerapi.models.CreditNote
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.CreditNoteEntity
import java.util.UUID

fun CreditNote.toEntity(cashRenditionId: UUID) =
    CreditNoteEntity(
        type = type,
        amount = amount,
        notes = notes,
        cashRenditionId = cashRenditionId,
        id = id,
    )

fun CreditNoteEntity.toDomain() =
    CreditNote(
        type = type,
        amount = amount,
        notes = notes,
        id = id!!,
    )
