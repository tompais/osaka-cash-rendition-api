package com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces

import com.osaka.cashbalancerapi.enums.Currency
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.ExchangeRateEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
interface IExchangeRateRepository : CoroutineCrudRepository<ExchangeRateEntity, UUID> {
    /**
     * Encuentra el tipo de cambio activo para una moneda en una fecha específica
     */
    @Query(
        """
        SELECT * FROM exchange_rates 
        WHERE currency = :currency 
        AND valid_from <= :dateTime 
        AND (valid_until IS NULL OR valid_until > :dateTime)
        ORDER BY valid_from DESC 
        LIMIT 1
        """,
    )
    suspend fun findActiveByCurrencyAndDateTime(
        currency: Currency,
        dateTime: LocalDateTime = LocalDateTime.now(),
    ): ExchangeRateEntity?

    /**
     * Encuentra todos los tipos de cambio activos actualmente
     */
    @Query(
        """
        SELECT * FROM exchange_rates 
        WHERE valid_until IS NULL
        """,
    )
    fun findAllActive(): Flow<ExchangeRateEntity>
}
