package com.osaka.cashbalancerapi.routers

import com.osaka.cashbalancerapi.handlers.ExchangeRateHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ExchangeRateRouter {
    @Bean
    fun exchangeRateRoutes(handler: ExchangeRateHandler) =
        coRouter {
            "/exchange-rates".nest {
                accept(MediaType.APPLICATION_JSON).nest {
                    // PUT /exchange-rates - Crear o actualizar exchange rate
                    PUT("", handler::createOrUpdate)

                    // GET /exchange-rates/{id} - Obtener exchange rate por ID
                    GET("/{id}", handler::findById)

                    // GET /exchange-rates/active/{currency} - Obtener exchange rate activo por moneda
                    GET("/active/{currency}", handler::findActiveRateByCurrency)

                    // PUT /exchange-rates/{id}/end - Finalizar exchange rate
                    PUT("/{id}/end", handler::endExchangeRate)
                }
            }
        }
}
