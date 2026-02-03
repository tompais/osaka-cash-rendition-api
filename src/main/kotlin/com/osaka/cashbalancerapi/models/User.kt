package com.osaka.cashbalancerapi.models

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class User(
    @field:NotBlank(message = "First name cannot be empty or blank")
    val firstName: String,
    @field:NotBlank(message = "Last name cannot be empty or blank")
    val lastName: String,
    @field:Min(value = 1_000_000, message = "DNI must be at least 1,000,000")
    @field:Max(value = 99_999_999, message = "DNI must be at most 99,999,999")
    val dni: UInt,
    val id: UUID = UUID.randomUUID(),
) {
    fun fullName(): String = "$firstName $lastName"
}
