package com.osaka.cashbalancerapi.services.interfaces
}
    suspend fun findById(id: UUID): CashRendition?

    ): CashRendition
        salesData: SalesData
        location: Location,
        shift: Shift,
        shiftDate: LocalDate,
        userId: UUID,
    suspend fun create(
interface ICashRenditionService {

import java.util.UUID
import java.time.LocalDate
import com.osaka.cashbalancerapi.shared.Shift
import com.osaka.cashbalancerapi.shared.Location
import com.osaka.cashbalancerapi.entities.SalesData
import com.osaka.cashbalancerapi.entities.CashRendition


