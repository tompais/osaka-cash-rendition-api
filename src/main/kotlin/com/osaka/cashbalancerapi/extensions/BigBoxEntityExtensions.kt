package com.osaka.cashbalancerapi.extensions

import com.osaka.cashbalancerapi.models.BigBox
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.BigBoxEntity

fun BigBox.toEntity() =
    BigBoxEntity(
        experienceType = experienceType,
        unitPrice = unitPrice,
        validFrom = validFrom,
        validUntil = validUntil,
        id = id,
    )

fun BigBoxEntity.toDomain() =
    BigBox(
        experienceType = experienceType,
        unitPrice = unitPrice,
        validFrom = validFrom,
        validUntil = validUntil,
        id = id!!,
    )
