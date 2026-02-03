package com.osaka.cashbalancerapi.extensions

import com.osaka.cashbalancerapi.models.User
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.UserEntity

fun User.toEntity() =
    UserEntity(
        firstName = firstName,
        lastName = lastName,
        dni = dni,
        id = id,
    )

fun UserEntity.toDomain() =
    User(
        firstName = firstName,
        lastName = lastName,
        dni = dni,
        id = id!!,
    )
