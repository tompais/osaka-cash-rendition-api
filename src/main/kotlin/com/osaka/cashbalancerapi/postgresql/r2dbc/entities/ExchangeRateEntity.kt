package com.osaka.cashbalancerapi.postgresql.r2dbc.entities

import com.osaka.cashbalancerapi.enums.Currency
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Table("exchange_rates")
data class ExchangeRateEntity(
    @Column("currency")
    val currency: Currency,
    @Column("rate_to_ars")
    val rateToArs: BigDecimal,
    @Column("valid_from")
    val validFrom: LocalDateTime = LocalDateTime.now(),
    @Column("valid_until")
    val validUntil: LocalDateTime? = null,
    @Id
    @Column("id")
    val id: UUID? = null,
)
