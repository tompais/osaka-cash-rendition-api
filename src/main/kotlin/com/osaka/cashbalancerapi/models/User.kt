package com.osaka.cashbalancerapi.entities

import java.util.UUID

data class User(
    val firstName: String,
    val lastName: String,
    val dni: UInt,
    val id: UUID = UUID.randomUUID(),
) {
    init {
        require(dni in 1_000_000u..99_999_999u) {
            "DNI must be between 1,000,000 and 99,999,999"
        }
    }

    fun fullName(): String = "$firstName $lastName"
}
