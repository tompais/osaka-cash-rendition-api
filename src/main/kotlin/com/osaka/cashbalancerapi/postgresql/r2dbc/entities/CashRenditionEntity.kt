package com.osaka.cashbalancerapi.entities

import com.osaka.cashbalancerapi.enums.Location
import com.osaka.cashbalancerapi.enums.Shift
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Table("cash_renditions")
data class CashRenditionEntity(
    @Column("shift")
    val shift: Shift,
    @Column("location")
    val location: Location,
    @Column("shift_date")
    val shiftDate: LocalDate = LocalDate.now(),
    @Column("created_by_user_id")
    val createdByUserId: UUID,
    // SalesData fields
    @Column("initial_balance")
    val initialBalance: BigDecimal = BigDecimal.ZERO,
    @Column("marketing")
    val marketing: BigDecimal = BigDecimal.ZERO,
    @Column("current_account")
    val currentAccount: BigDecimal = BigDecimal.ZERO,
    @Id
    @Column("id")
    val id: UUID = UUID.randomUUID(),
    // Relaciones OneToMany
    @MappedCollection(idColumn = "cash_rendition_id")
    val reliefs: Set<ReliefEntity> = emptySet(),
    @MappedCollection(idColumn = "cash_rendition_id")
    val invoiceSales: Set<InvoiceSaleEntity> = emptySet(),
    @MappedCollection(idColumn = "cash_rendition_id")
    val deliverySales: Set<DeliverySaleEntity> = emptySet(),
    @MappedCollection(idColumn = "cash_rendition_id")
    val bigBoxSales: Set<BigBoxSaleEntity> = emptySet(),
)
