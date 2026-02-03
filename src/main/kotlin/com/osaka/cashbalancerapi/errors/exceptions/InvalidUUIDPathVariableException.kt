package com.osaka.cashbalancerapi.errors.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class InvalidUUIDPathVariableException(
    parameterName: String,
    value: String,
    cause: Throwable,
) : ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "Invalid path variable '$parameterName': value '$value' is not a valid UUID",
        cause,
    )
