package com.osaka.cashbalancerapi.extensions

import com.osaka.cashbalancerapi.models.CashRendition
import com.osaka.cashbalancerapi.responses.CashRenditionResponse

fun CashRendition.toResponse() =
    CashRenditionResponse(
        id = id,
        userId = createdBy.id,
        shift = shift,
        location = location,
        shiftDate = shiftDate,
        salesData = salesData,
        additionalData = additionalData,
        reliefs = reliefs,
        paymentMethodTransactions = paymentMethodTransactions,
        totalPaymentMethods = totalPaymentMethods(),
        totalReliefsInArs = totalReliefsInArs(),
        totalCashAvailable = totalCashAvailable(),
        totalActualCash = totalActualCash(),
        cashDifference = cashDifference(),
        finalBalance = finalBalance(),
    )
