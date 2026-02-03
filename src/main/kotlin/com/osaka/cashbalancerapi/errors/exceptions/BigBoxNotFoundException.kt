package com.osaka.cashbalancerapi.errors.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

class BigBoxNotFoundException(
    id: UUID,
) : ResponseStatusException(HttpStatus.NOT_FOUND, "BigBox with id $id not found")
