package com.osaka.cashbalancerapi.postgresql.r2dbc.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.util.UUID

@Table("big_box_sales")
data class BigBoxSaleEntity(
    @Column("big_box_id")
    val bigBoxId: UUID,
    @Column("quantity")
    val quantity: UInt,
    @Column("unit_price_snapshot")
    val unitPriceSnapshot: BigDecimal,
    @Column("cash_rendition_id")
    val cashRenditionId: UUID,
    @Id
    @Column("id")
    val id: UUID? = null,
)
