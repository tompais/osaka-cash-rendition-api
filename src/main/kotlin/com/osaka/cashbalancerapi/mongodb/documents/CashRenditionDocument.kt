package com.osaka.cashbalancerapi.repositories.mongodb

import com.osaka.cashbalancerapi.entities.SalesData
import com.osaka.cashbalancerapi.shared.Location
import com.osaka.cashbalancerapi.shared.Shift
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.UUID

@Document(collection = "cash_renditions")
@CompoundIndex(
    name = "shift_date_location_shift_idx",
    def = "{'shiftDate': 1, 'location': 1, 'shift': 1}",
    unique = true,
)
data class CashRenditionDocument(
    val shift: Shift,
    val location: Location,
    val salesData: SalesData, // ← Usa directamente la entidad de dominio
    @DBRef
    val createdBy: UserDocument,
    val shiftDate: LocalDate = LocalDate.now(),
    @Id
    val id: UUID = UUID.randomUUID(),
)
