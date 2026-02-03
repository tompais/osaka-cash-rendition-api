package com.osaka.cashbalancerapi.extensions

import com.osaka.cashbalancerapi.errors.exceptions.InvalidUUIDPathVariableException
import org.springframework.web.reactive.function.server.ServerRequest
import java.util.UUID

/**
 * Obtiene un path variable y lo convierte a UUID de forma segura.
 * Lanza InvalidPathVariableException si el valor no es un UUID válido.
 *
 * @param name Nombre del path variable
 * @return UUID parseado
 * @throws InvalidUUIDPathVariableException si el valor no es un UUID válido
 */
fun ServerRequest.getUUIDPathVariable(name: String): UUID =
    pathVariable(name).let { value ->
        try {
            UUID.fromString(value)
        } catch (e: IllegalArgumentException) {
            throw InvalidUUIDPathVariableException(name, value, e)
        }
    }
