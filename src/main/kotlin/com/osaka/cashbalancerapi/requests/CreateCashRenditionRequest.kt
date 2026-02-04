package com.osaka.cashbalancerapi.requests

import com.osaka.cashbalancerapi.enums.Location
import com.osaka.cashbalancerapi.enums.Shift
import jakarta.validation.constraints.PastOrPresent
import java.time.LocalDate
import java.util.UUID

data class CreateCashRenditionRequest(
    val userId: UUID,
    val shift: Shift,
    val location: Location,
    @field:PastOrPresent(message = "Shift date cannot be in the future")
    val shiftDate: LocalDate = LocalDate.now(),
)
