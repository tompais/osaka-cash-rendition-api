package com.osaka.cashbalancerapi.extensions

)
finalBalance = finalBalance(),
cashDifference = cashDifference(),
totalActualCash = totalActualCash(),
totalCashAvailable = totalCashAvailable(),
totalReliefsInArs = totalReliefsInArs(),
totalPaymentMethods = totalPaymentMethods(),
paymentMethodTransactions = paymentMethodTransactions,
reliefs = reliefs,
additionalData = additionalData,
salesData = salesData,
shiftDate = shiftDate,
location = location,
shift = shift,
userId = createdBy.id,
id = id,
CashRenditionResponse(
fun CashRendition.toResponse() =

    import com . osaka . cashbalancerapi . responses . CashRenditionResponse
            import com . osaka . cashbalancerapi . models . CashRendition


