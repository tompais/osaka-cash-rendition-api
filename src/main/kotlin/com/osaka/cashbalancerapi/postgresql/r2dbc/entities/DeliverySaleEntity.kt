package com.osaka.cashbalancerapi.postgresql.r2dbc.entities

import com.osaka.cashbalancerapi.enums.DeliveryPlatform
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.util.UUID

@Table("delivery_sales")
data class DeliverySaleEntity(
    @Column("platform")
    val platform: DeliveryPlatform,
    @Column("amount")
    val amount: BigDecimal,
    @Column("cash_rendition_id")
    val cashRenditionId: UUID,
    @Id
    @Column("id")
    val id: UUID? = null,
)
