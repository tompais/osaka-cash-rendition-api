package com.osaka.cashbalancerapi.services.interfaces

import com.osaka.cashbalancerapi.enums.Currency
import com.osaka.cashbalancerapi.models.ExchangeRate
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

interface IExchangeRateService {
    /**
     * Encuentra el tipo de cambio activo para una moneda en el momento actual
     * Lanza ExchangeRateNotFoundException si no se encuentra
     */
    suspend fun findActiveRateByCurrency(currency: Currency): ExchangeRate

    /**
     * Encuentra el tipo de cambio activo para una moneda en una fecha específica
     * Lanza ExchangeRateNotFoundException si no se encuentra
     */
    suspend fun findActiveRateByCurrencyAndDateTime(
        currency: Currency,
        dateTime: LocalDateTime,
    ): ExchangeRate

    /**
     * Encuentra todos los tipos de cambio actualmente vigentes
     */
    fun findAllActiveRates(): Flow<ExchangeRate>

    /**
     * Crea o actualiza un tipo de cambio
     */
    suspend fun save(exchangeRate: ExchangeRate): ExchangeRate

    /**
     * Encuentra un tipo de cambio por ID y lanza ExchangeRateNotFoundException si no existe
     * @throws ExchangeRateNotFoundException si el tipo de cambio no existe
     */
    suspend fun safeFindById(id: UUID): ExchangeRate

    /**
     * Invalida un tipo de cambio estableciendo su fecha de fin
     */
    suspend fun invalidateRate(
        id: UUID,
        validUntil: LocalDateTime,
    ): ExchangeRate

    /**
     * Crea o actualiza un tipo de cambio para una moneda
     * Si ya existe un tipo de cambio activo para la moneda, lo finaliza y crea uno nuevo
     */
    suspend fun createOrUpdate(
        currency: Currency,
        rateToArs: BigDecimal,
        validFrom: LocalDate = LocalDate.now(),
    ): ExchangeRate

    /**
     * Finaliza un tipo de cambio estableciendo su fecha de fin
     * @throws ExchangeRateNotFoundException si el tipo de cambio no existe
     */
    suspend fun endExchangeRate(
        id: UUID,
        validUntil: LocalDate,
    ): ExchangeRate
}
