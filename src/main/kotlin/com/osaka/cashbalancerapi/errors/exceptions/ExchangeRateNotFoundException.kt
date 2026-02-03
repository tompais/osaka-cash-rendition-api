package com.osaka.cashbalancerapi.errors.exceptions

import com.osaka.cashbalancerapi.enums.Currency
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

class ExchangeRateNotFoundException : ResponseStatusException {
    constructor(currency: Currency) : super(
        HttpStatus.NOT_FOUND,
        "No active exchange rate found for currency: $currency",
    )

    constructor(id: UUID) : super(
        HttpStatus.NOT_FOUND,
        "Exchange rate not found with ID: $id",
    )
}
