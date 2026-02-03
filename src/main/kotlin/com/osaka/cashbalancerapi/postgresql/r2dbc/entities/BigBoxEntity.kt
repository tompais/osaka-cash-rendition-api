package com.osaka.cashbalancerapi.postgresql.r2dbc.entities

import com.osaka.cashbalancerapi.enums.ExperienceType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Table("big_boxes")
data class BigBoxEntity(
    @Column("experience_type")
    val experienceType: ExperienceType,
    @Column("unit_price")
    val unitPrice: BigDecimal,
    @Column("valid_from")
    val validFrom: LocalDateTime,
    @Column("valid_until")
    val validUntil: LocalDateTime? = null,
    @Id
    @Column("id")
    val id: UUID? = null,
)
