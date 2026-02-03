package com.osaka.cashbalancerapi.entities

import com.osaka.cashbalancerapi.models.DeliverySale
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.util.UUID

@Table("delivery_sales")
data class DeliverySaleEntity(
    @Column("platform")
    val platform: DeliverySale.DeliveryPlatform,
    @Column("amount")
    val amount: BigDecimal,
    @Id
    @Column("id")
    val id: UUID = UUID.randomUUID(),
)
