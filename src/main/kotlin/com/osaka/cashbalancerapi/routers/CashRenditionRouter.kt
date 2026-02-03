package com.osaka.cashbalancerapi.routers

import com.osaka.cashbalancerapi.handlers.CashRenditionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class CashRenditionRouter {
    @Bean
    fun cashRenditionRoutes(handler: CashRenditionHandler) =
        coRouter {
            "/cash-renditions".nest {
                accept(MediaType.APPLICATION_JSON).nest {
                    // POST /cash-renditions - Crear nuevo rendimiento
                    POST("", handler::create)

                    // GET /cash-renditions/{id} - Obtener rendimiento por ID
                    GET("/{id}", handler::findById)

                    // PUT /cash-renditions/{id}/reliefs - Agregar/actualizar alivio
                    PUT("/{id}/reliefs", handler::addOrUpdateRelief)

                    // GET /cash-renditions/{id}/reliefs - Obtener alivios
                    GET("/{id}/reliefs", handler::getReliefs)

                    // PUT /cash-renditions/{id}/credit-notes - Agregar/actualizar nota de crédito
                    PUT("/{id}/credit-notes", handler::addOrUpdateCreditNote)

                    // PUT /cash-renditions/{id}/payment-methods - Agregar/actualizar método de pago
                    PUT("/{id}/payment-methods", handler::addOrUpdatePaymentMethodTransaction)

                    // PUT /cash-renditions/{id}/invoice-sales - Agregar/actualizar venta con factura
                    PUT("/{id}/invoice-sales", handler::addOrUpdateInvoiceSale)

                    // PUT /cash-renditions/{id}/delivery-sales - Agregar/actualizar venta por delivery
                    PUT("/{id}/delivery-sales", handler::addOrUpdateDeliverySale)
                }
            }
        }
}
