package com.osaka.cashbalancerapi.postgresql.r2dbc.entities

import com.osaka.cashbalancerapi.enums.PaymentMethodType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.util.UUID

@Table("payment_method_transactions")
data class PaymentMethodTransactionEntity(
    @Column("payment_method_type")
    val paymentMethodType: PaymentMethodType,
    @Column("amount")
    val amount: BigDecimal,
    @Column("cash_rendition_id")
    val cashRenditionId: UUID,
    @Id
    @Column("id")
    val id: UUID? = null,
)
