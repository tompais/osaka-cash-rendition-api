package com.osaka.cashbalancerapi.postgresql.r2dbc.entities

import com.osaka.cashbalancerapi.enums.Location
import com.osaka.cashbalancerapi.enums.Shift
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
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
    // AdditionalData fields
    @Column("salon_otoshis")
    val salonOtoshis: UInt = 0u,
    @Column("dely_osk_ohashis")
    val delyOskOhashis: UInt = 0u,
    @Column("dely_osk_orders")
    val delyOskOrders: UInt = 0u,
    @Column("dely_nt_orders")
    val delyNtOrders: UInt = 0u,
    @Column("dely_nt_ohashis")
    val delyNtOhashis: UInt = 0u,
    @Id
    @Column("id")
    val id: UUID? = null,
)
