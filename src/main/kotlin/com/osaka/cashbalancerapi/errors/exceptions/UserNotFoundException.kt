package com.osaka.cashbalancerapi.errors.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

class UserNotFoundException(
    userId: UUID,
) : ResponseStatusException(HttpStatus.NOT_FOUND, "User with id $userId not found")
