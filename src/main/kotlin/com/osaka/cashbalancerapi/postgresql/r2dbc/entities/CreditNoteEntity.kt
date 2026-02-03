package com.osaka.cashbalancerapi.postgresql.r2dbc.entities

import com.osaka.cashbalancerapi.enums.CreditNoteType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.util.UUID

@Table("credit_notes")
data class CreditNoteEntity(
    @Column("type")
    val type: CreditNoteType,
    @Column("amount")
    val amount: BigDecimal,
    @Column("notes")
    val notes: String? = null,
    @Column("cash_rendition_id")
    val cashRenditionId: UUID,
    @Id
    @Column("id")
    val id: UUID? = null,
)
