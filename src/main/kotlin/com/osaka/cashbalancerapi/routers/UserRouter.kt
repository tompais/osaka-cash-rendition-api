package com.osaka.cashbalancerapi.routers

import com.osaka.cashbalancerapi.handlers.UserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class UserRouter {
    @Bean
    fun userRoutes(handler: UserHandler) =
        coRouter {
            "/users".nest {
                accept(MediaType.APPLICATION_JSON).nest {
                    // POST /users - Crear nuevo usuario
                    POST("", handler::create)

                    // GET /users/{id} - Obtener usuario por ID
                    GET("/{id}", handler::findById)

                    // GET /users/dni/{dni} - Buscar usuario por DNI
                    GET("/dni/{dni}", handler::findByDni)
                }
            }
        }
}
