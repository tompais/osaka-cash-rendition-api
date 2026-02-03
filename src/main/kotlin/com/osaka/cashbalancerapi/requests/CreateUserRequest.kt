package com.osaka.cashbalancerapi.requests

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class CreateUserRequest(
    @field:NotBlank(message = "First name cannot be blank")
    val firstName: String,
    @field:NotBlank(message = "Last name cannot be blank")
    val lastName: String,
    @field:Min(value = 1000000, message = "DNI must be at least 1000000")
    @field:Max(value = 99999999, message = "DNI must be at most 99999999")
    val dni: UInt,
)
