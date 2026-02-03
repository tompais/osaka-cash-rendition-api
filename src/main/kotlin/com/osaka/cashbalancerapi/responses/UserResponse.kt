package com.osaka.cashbalancerapi.responses

import java.util.UUID

data class UserResponse(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val dni: UInt,
    val fullName: String,
)
