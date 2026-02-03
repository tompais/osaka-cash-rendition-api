package com.osaka.cashbalancerapi.handlers

import com.osaka.cashbalancerapi.enums.Currency
import com.osaka.cashbalancerapi.extensions.awaitBodyAndValidate
import com.osaka.cashbalancerapi.extensions.getUUIDPathVariable
import com.osaka.cashbalancerapi.extensions.toResponse
import com.osaka.cashbalancerapi.requests.CreateExchangeRateRequest
import com.osaka.cashbalancerapi.requests.EndExchangeRateRequest
import com.osaka.cashbalancerapi.services.interfaces.IExchangeRateService
import com.osaka.cashbalancerapi.utils.constants.keys.PathVariableKey
import jakarta.validation.Validator
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class ExchangeRateHandler(
    private val exchangeRateService: IExchangeRateService,
    private val validator: Validator,
) {
    /**
     * Crea o actualiza un exchange rate
     * POST /exchange-rates
     */
    suspend fun createOrUpdate(request: ServerRequest): ServerResponse {
        val createRequest = request.awaitBodyAndValidate<CreateExchangeRateRequest>(validator)

        val exchangeRate =
            exchangeRateService.createOrUpdate(
                createRequest.currency,
                createRequest.rateToArs,
                createRequest.validFrom,
            )

        return ServerResponse
            .status(HttpStatus.CREATED)
            .bodyValueAndAwait(exchangeRate.toResponse())
    }

    /**
     * Obtiene un exchange rate por ID
     * GET /exchange-rates/{id}
     */
    suspend fun findById(request: ServerRequest): ServerResponse {
        val id = request.getUUIDPathVariable(PathVariableKey.ID)
        val exchangeRate = exchangeRateService.safeFindById(id)
        return ServerResponse.ok().bodyValueAndAwait(exchangeRate.toResponse())
    }

    /**
     * Obtiene el exchange rate activo para una moneda
     * GET /exchange-rates/active/{currency}
     */
    suspend fun findActiveRateByCurrency(request: ServerRequest): ServerResponse {
        val currency = Currency.valueOf(request.pathVariable("currency"))

        val exchangeRate = exchangeRateService.findActiveRateByCurrency(currency)

        return ServerResponse.ok().bodyValueAndAwait(exchangeRate.toResponse())
    }

    /**
     * Finaliza un exchange rate (le pone validUntil)
     * PUT /exchange-rates/{id}/end
     */
    suspend fun endExchangeRate(request: ServerRequest): ServerResponse {
        val id = request.getUUIDPathVariable(PathVariableKey.ID)
        val endRequest = request.awaitBodyAndValidate<EndExchangeRateRequest>(validator)
        val exchangeRate = exchangeRateService.endExchangeRate(id, endRequest.validUntil)
        return ServerResponse.ok().bodyValueAndAwait(exchangeRate.toResponse())
    }
}
