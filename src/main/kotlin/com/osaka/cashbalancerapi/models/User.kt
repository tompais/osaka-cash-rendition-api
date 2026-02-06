package com.osaka.cashbalancerapi.models

import java.util.UUID

data class User(
    val firstName: String,
    val lastName: String,
    val dni: UInt,
    val id: UUID = UUID.randomUUID(),
) {
    fun fullName(): String = "$firstName $lastName"
}
