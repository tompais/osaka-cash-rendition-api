package com.osaka.cashbalancerapi.errors.exceptions

import jakarta.validation.ConstraintViolation
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ValidationException(
    violations: Set<ConstraintViolation<*>>,
) : ResponseStatusException(HttpStatus.BAD_REQUEST, formatViolations(violations)) {
    companion object {
        private fun formatViolations(violations: Set<ConstraintViolation<*>>): String =
            violations.joinToString("; ") { violation ->
                "${violation.propertyPath}: ${violation.message}"
            }
    }
}
