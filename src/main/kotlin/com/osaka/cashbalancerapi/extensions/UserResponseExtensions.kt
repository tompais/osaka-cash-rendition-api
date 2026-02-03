package com.osaka.cashbalancerapi.extensions

import com.osaka.cashbalancerapi.models.User
import com.osaka.cashbalancerapi.responses.UserResponse

fun User.toResponse() =
    UserResponse(
        id = id,
        firstName = firstName,
        lastName = lastName,
        dni = dni,
        fullName = fullName(),
    )
