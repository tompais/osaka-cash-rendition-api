package com.osaka.cashbalancerapi.entities

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
    @Id
    @Column("id")
    val id: UUID = UUID.randomUUID(),
)
