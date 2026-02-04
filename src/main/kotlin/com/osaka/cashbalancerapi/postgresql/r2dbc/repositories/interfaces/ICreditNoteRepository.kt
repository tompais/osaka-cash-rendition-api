package com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces

import com.osaka.cashbalancerapi.enums.CreditNoteType
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.CreditNoteEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface ICreditNoteRepository : CoroutineCrudRepository<CreditNoteEntity, UUID> {
    fun findAllByCashRenditionId(cashRenditionId: UUID): Flow<CreditNoteEntity>

    suspend fun findByTypeAndCashRenditionId(
        type: CreditNoteType,
        cashRenditionId: UUID,
    ): CreditNoteEntity?
}
