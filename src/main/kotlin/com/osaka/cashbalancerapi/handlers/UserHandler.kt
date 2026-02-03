package com.osaka.cashbalancerapi.handlers

import com.osaka.cashbalancerapi.extensions.awaitBodyAndValidate
import com.osaka.cashbalancerapi.extensions.getUUIDPathVariable
import com.osaka.cashbalancerapi.extensions.toResponse
import com.osaka.cashbalancerapi.requests.CreateUserRequest
import com.osaka.cashbalancerapi.services.interfaces.IUserService
import com.osaka.cashbalancerapi.utils.constants.keys.PathVariableKey
import jakarta.validation.Validator
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class UserHandler(
    private val userService: IUserService,
    private val validator: Validator,
) {
    /**
     * Crea un nuevo usuario
     * POST /users
     */
    suspend fun create(request: ServerRequest): ServerResponse {
        val createRequest = request.awaitBodyAndValidate<CreateUserRequest>(validator)

        val user =
            userService.create(
                firstName = createRequest.firstName,
                lastName = createRequest.lastName,
                dni = createRequest.dni,
            )

        return ServerResponse
            .status(HttpStatus.CREATED)
            .bodyValueAndAwait(user.toResponse())
    }

    /**
     * Obtiene un usuario por ID
     * GET /users/{id}
     */
    suspend fun findById(request: ServerRequest): ServerResponse {
        val id = request.getUUIDPathVariable(PathVariableKey.ID)
        val user = userService.safeFindById(id)
        return ServerResponse.ok().bodyValueAndAwait(user.toResponse())
    }

    /**
     * Busca un usuario por DNI
     * GET /api/users/dni/{dni}
     */
    suspend fun findByDni(request: ServerRequest): ServerResponse {
        val dni = request.pathVariable("dni").toUInt()

        val user = userService.findByDni(dni)

        return if (user != null) {
            ServerResponse.ok().bodyValueAndAwait(user.toResponse())
        } else {
            ServerResponse.notFound().buildAndAwait()
        }
    }
}
