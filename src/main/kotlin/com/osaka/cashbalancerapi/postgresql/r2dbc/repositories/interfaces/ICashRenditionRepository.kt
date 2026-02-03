package com.osaka.cashbalancerapi.r2dbc.repositories.interfaces

import com.osaka.cashbalancerapi.entities.CashRenditionEntity
import com.osaka.cashbalancerapi.enums.Location
import com.osaka.cashbalancerapi.enums.Shift
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.UUID

@Repository
interface ICashRenditionRepository : CoroutineCrudRepository<CashRenditionEntity, UUID> {
    suspend fun findByShiftDateAndLocationAndShift(
        shiftDate: LocalDate,
        location: Location,
        shift: Shift,
    ): CashRenditionEntity?
}
