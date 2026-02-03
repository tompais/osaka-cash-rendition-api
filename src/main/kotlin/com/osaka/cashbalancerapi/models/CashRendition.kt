package com.osaka.cashbalancerapi.entities

import com.osaka.cashbalancerapi.shared.Location
import com.osaka.cashbalancerapi.shared.Shift
import java.time.LocalDate
import java.util.UUID

/**
 * Rendimiento de Caja
 * Representa el registro de ventas y movimientos de caja para un turno específico
 */
data class CashRendition(
    val createdBy: UserSnapshot,
    /** Turno (AM/PM) */
    val shift: Shift,
    /** Local donde se realizó el rendimiento */
    val location: Location,
    /** Datos de ventas del turno */
    val salesData: SalesData,
    /** Fecha del turno que se está rindiendo */
    val shiftDate: LocalDate = LocalDate.now(),
    val id: UUID = UUID.randomUUID(),
) {
    data class UserSnapshot(
        val userId: UUID,
        val fullName: String,
        val dni: String,
    ) {
        companion object {
            fun from(user: User) =
                UserSnapshot(
                    userId = user.id,
                    fullName = user.fullName(),
                    dni = user.dni.toString(),
                )
        }
    }

    companion object {
        fun create(
            createdBy: User,
            shift: Shift,
            location: Location,
            salesData: SalesData,
            shiftDate: LocalDate = LocalDate.now(),
            id: UUID = UUID.randomUUID(),
        ): CashRendition =
            CashRendition(
                id = id,
                createdBy = UserSnapshot.from(createdBy),
                shiftDate = shiftDate,
                shift = shift,
                location = location,
                salesData = salesData,
            )
    }
}
