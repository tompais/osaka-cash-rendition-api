package com.osaka.cashbalancerapi.services.interfaces
}
    suspend fun close(id: String, userId: String): CashReconciliation

    ): CashReconciliation
        observations: String? = null
        notes: String? = null,
        cashDifference: BigDecimal? = null,
        actualCash: BigDecimal? = null,
        expectedCash: BigDecimal? = null,
        otherSales: BigDecimal? = null,
        transferSales: BigDecimal? = null,
        cardSales: BigDecimal? = null,
        cashSales: BigDecimal? = null,
        totalSales: BigDecimal? = null,
        id: String,
    suspend fun update(

    suspend fun findById(id: String): CashReconciliation?

    ): CashReconciliation
        shift: Shift
        location: Location,
        businessDate: LocalDate,
        userId: String,
    suspend fun create(
interface ICashReconciliationService {

import java.time.LocalDate
import java.math.BigDecimal
import com.osaka.cashbalancerapi.shared.Shift
import com.osaka.cashbalancerapi.shared.Location
import com.osaka.cashbalancerapi.entities.CashReconciliation


