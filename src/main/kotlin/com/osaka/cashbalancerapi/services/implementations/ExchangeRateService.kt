package com.osaka.cashbalancerapi.services.implementations

import com.osaka.cashbalancerapi.enums.Currency
import com.osaka.cashbalancerapi.errors.exceptions.ExchangeRateNotFoundException
import com.osaka.cashbalancerapi.extensions.toDomain
import com.osaka.cashbalancerapi.extensions.toEntity
import com.osaka.cashbalancerapi.models.ExchangeRate
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.ExchangeRateEntity
import com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces.IExchangeRateRepository
import com.osaka.cashbalancerapi.services.interfaces.IExchangeRateService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Service
class ExchangeRateService(
    private val exchangeRateRepository: IExchangeRateRepository,
) : IExchangeRateService {
    override suspend fun findActiveRateByCurrency(currency: Currency): ExchangeRate =
        findActiveRateByCurrencyAndDateTime(currency, LocalDateTime.now())

    override suspend fun findActiveRateByCurrencyAndDateTime(
        currency: Currency,
        dateTime: LocalDateTime,
    ): ExchangeRate =
        exchangeRateRepository
            .findActiveByCurrencyAndDateTime(currency, dateTime)
            ?.toDomain()
            ?: throw ExchangeRateNotFoundException(currency)

    override fun findAllActiveRates(): Flow<ExchangeRate> =
        exchangeRateRepository
            .findAllActive()
            .map(ExchangeRateEntity::toDomain)

    override suspend fun save(exchangeRate: ExchangeRate): ExchangeRate =
        exchangeRateRepository
            .save(exchangeRate.toEntity())
            .toDomain()

    private suspend fun findById(id: UUID): ExchangeRate? =
        exchangeRateRepository
            .findById(id)
            ?.toDomain()

    override suspend fun safeFindById(id: UUID): ExchangeRate = findById(id) ?: throw ExchangeRateNotFoundException(id)

    override suspend fun invalidateRate(
        id: UUID,
        validUntil: LocalDateTime,
    ): ExchangeRate {
        val existingRate = safeFindById(id)
        val updatedRate = existingRate.copy(validUntil = validUntil)
        return save(updatedRate)
    }

    override suspend fun createOrUpdate(
        currency: Currency,
        rateToArs: BigDecimal,
        validFrom: LocalDate,
    ): ExchangeRate {
        // Intentar finalizar cualquier tipo de cambio activo para esta moneda
        try {
            val activeRate = findActiveRateByCurrency(currency)
            // Si existe un activo, finalizarlo
            invalidateRate(activeRate.id, validFrom.atStartOfDay().minusSeconds(1))
        } catch (_: ExchangeRateNotFoundException) {
            // No hay tipo de cambio activo, continuar
        }

        // Crear el nuevo tipo de cambio
        val newExchangeRate =
            ExchangeRate(
                currency = currency,
                rateToArs = rateToArs,
                validFrom = validFrom.atStartOfDay(),
                validUntil = null,
            )

        return save(newExchangeRate)
    }

    override suspend fun endExchangeRate(
        id: UUID,
        validUntil: LocalDate,
    ): ExchangeRate {
        val existingRate = safeFindById(id)
        val updatedRate = existingRate.copy(validUntil = validUntil.atTime(23, 59, 59))
        return save(updatedRate)
    }
}
