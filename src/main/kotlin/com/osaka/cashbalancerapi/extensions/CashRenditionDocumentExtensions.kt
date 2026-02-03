package com.osaka.cashbalancerapi.extensions

import com.osaka.cashbalancerapi.models.CashRendition
import com.osaka.cashbalancerapi.mongodb.documents.CashRenditionDocument

fun CashRenditionDocument.toDomain() =
    CashRendition(
        createdBy = createdBy.toDomain(),
        shift = shift,
        location = location,
        salesData = salesData,
        shiftDate = shiftDate,
        id = id,
    )
