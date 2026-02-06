package com.osaka.cashbalancerapi.responses

import com.osaka.cashbalancerapi.enums.Location
import com.osaka.cashbalancerapi.enums.Shift
import com.osaka.cashbalancerapi.models.PaymentMethodTransaction
import com.osaka.cashbalancerapi.models.Relief
import com.osaka.cashbalancerapi.models.SalesData
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class CashRenditionResponse(
    val id: UUID,
    val userId: UUID,
    val shift: Shift,
    val location: Location,
    val shiftDate: LocalDate,
    val salesData: SalesData,
    val reliefs: List<Relief>,
    val paymentMethodTransactions: List<PaymentMethodTransaction>,
    val totalPaymentMethods: BigDecimal,
    val totalReliefsInArs: BigDecimal,
    val totalCashAvailable: BigDecimal,
    val totalActualCash: BigDecimal,
    val cashDifference: BigDecimal,
    val finalBalance: BigDecimal,
)
