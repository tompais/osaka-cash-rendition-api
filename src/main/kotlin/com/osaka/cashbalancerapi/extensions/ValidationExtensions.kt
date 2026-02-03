package com.osaka.cashbalancerapi.extensions

import com.osaka.cashbalancerapi.errors.exceptions.ValidationException
import jakarta.validation.Validator
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.awaitBody

/**
 * Extension function para validar un request body usando Jakarta Validation.
 * Lanza ValidationException si hay errores de validación.
 */
suspend inline fun <reified T : Any> ServerRequest.awaitBodyAndValidate(validator: Validator): T {
    val body = awaitBody<T>()
    val violations = validator.validate(body).toSet()

    if (violations.isNotEmpty()) {
        throw ValidationException(violations)
    }

    return body
}
