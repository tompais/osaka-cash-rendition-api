package com.osaka.cashbalancerapi.entities

import com.osaka.cashbalancerapi.models.InvoiceSale
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.util.UUID

@Table("invoice_sales")
data class InvoiceSaleEntity(
    @Column("type")
    val type: InvoiceSale.InvoiceType,
    @Column("amount")
    val amount: BigDecimal,
    @Id
    @Column("id")
    val id: UUID = UUID.randomUUID(),
)
