package com.osaka.cashbalancerapi.errors.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

class CashRenditionNotFoundException(
    renditionId: UUID,
) : ResponseStatusException(HttpStatus.NOT_FOUND, "Cash rendition with id $renditionId not found")
