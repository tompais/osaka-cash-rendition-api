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
                // Todos los endpoints producen JSON
                contentType(MediaType.APPLICATION_JSON).nest {
                    // Rutas GET (solo producen JSON, no lo consumen)
                    "/{id}".nest {
                        // GET /cash-renditions/{id} - Obtener rendimiento por ID
                        GET("", handler::findById)

                        // GET /cash-renditions/{id}/reliefs - Obtener alivios
                        GET("/reliefs", handler::getReliefs)
                    }

                    // Rutas que consumen JSON (POST, PUT, PATCH)
                    accept(MediaType.APPLICATION_JSON).nest {
                        // POST /cash-renditions - Crear nuevo rendimiento
                        POST("", handler::create)

                        // Rutas con {id}
                        "/{id}".nest {
                            // PUT /cash-renditions/{id}/payment-methods - Agregar/actualizar método de pago
                            PUT("/payment-methods", handler::addOrUpdatePaymentMethodTransaction)

                            // Rutas de Reliefs (Alivios)
                            "/reliefs".nest {
                                // PUT /cash-renditions/{id}/reliefs - Agregar/actualizar alivio
                                PUT("", handler::addOrUpdateRelief)
                            }

                            // Rutas de Sales Data
                            "/sales".nest {
                                // PUT /cash-renditions/{id}/sales/invoices - Agregar/actualizar venta con factura
                                PUT("/invoices", handler::addOrUpdateInvoiceSale)

                                // PUT /cash-renditions/{id}/sales/deliveries - Agregar/actualizar venta por delivery
                                PUT("/deliveries", handler::addOrUpdateDeliverySale)

                                // PUT /cash-renditions/{id}/sales/initial-balance - Actualizar saldo inicial
                                PUT("/initial-balance", handler::updateInitialBalance)

                                // PUT /cash-renditions/{id}/sales/marketing - Actualizar marketing
                                PUT("/marketing", handler::updateMarketing)

                                // PUT /cash-renditions/{id}/sales/current-account - Actualizar cuenta corriente
                                PUT("/current-account", handler::updateCurrentAccount)

                                // PUT /cash-renditions/{id}/sales/credit-notes - Agregar/actualizar nota de crédito
                                PUT("/credit-notes", handler::addOrUpdateCreditNote)

                                // PUT /cash-renditions/{id}/sales/lounge - Actualizar datos del lounge
                                PUT("/lounge", handler::updateLoungeData)

                                // Rutas de Pedidos (Orders)
                                "/orders".nest {
                                    // PUT /cash-renditions/{id}/sales/orders/osaka - Actualizar pedidos de Osaka
                                    PUT("/osaka", handler::updateOsakaOrders)

                                    // PUT /cash-renditions/{id}/sales/orders/nori-taco - Actualizar pedidos de Nori Taco
                                    PUT("/nori-taco", handler::updateNoriTacoOrders)
                                }
                            }
                        }
                    }
                }
            }
        }
}
