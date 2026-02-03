package com.osaka.cashbalancerapi.entities

import com.osaka.cashbalancerapi.enums.Currency
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.util.UUID

@Table("reliefs")
data class ReliefEntity(
    @Column(value = "envelope_number")
    val envelopeNumber: String,
    @Column("amount")
    val amount: BigDecimal,
    @Column("currency")
    val currency: Currency = Currency.ARS,
    @Id
    @Column("id")
    val id: UUID = UUID.randomUUID(),
)
