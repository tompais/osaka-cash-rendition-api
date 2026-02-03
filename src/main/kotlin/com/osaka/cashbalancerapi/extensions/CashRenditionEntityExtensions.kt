package com.osaka.cashbalancerapi.extensions

import com.osaka.cashbalancerapi.models.AdditionalData
import com.osaka.cashbalancerapi.models.CashRendition
import com.osaka.cashbalancerapi.models.DeliveryNoriTacoData
import com.osaka.cashbalancerapi.models.DeliveryOsakaData
import com.osaka.cashbalancerapi.models.DiningRoomData
import com.osaka.cashbalancerapi.models.SalesData
import com.osaka.cashbalancerapi.models.User
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.BigBoxSaleEntity
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.CashRenditionEntity
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.CreditNoteEntity
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.DeliverySaleEntity
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.InvoiceSaleEntity
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.PaymentMethodTransactionEntity
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.ReliefEntity

fun CashRendition.toEntity() =
    CashRenditionEntity(
        shift = shift,
        location = location,
        shiftDate = shiftDate,
        createdByUserId = createdBy.id,
        initialBalance = salesData.initialBalance,
        marketing = salesData.marketing,
        currentAccount = salesData.currentAccount,
        salonOtoshis = additionalData.diningRoomData.otoshis,
        delyOskOhashis = additionalData.deliveryOsakaData.ohashis,
        delyOskOrders = additionalData.deliveryOsakaData.orders,
        delyNtOrders = additionalData.deliveryNoriTacoData.orders,
        delyNtOhashis = additionalData.deliveryNoriTacoData.ohashis,
    )

fun CashRenditionEntity.toDomain(
    createdBy: User,
    reliefs: List<ReliefEntity> = emptyList(),
    invoiceSales: List<InvoiceSaleEntity> = emptyList(),
    deliverySales: List<DeliverySaleEntity> = emptyList(),
    bigBoxSales: List<BigBoxSaleEntity> = emptyList(),
    creditNotes: List<CreditNoteEntity> = emptyList(),
    paymentMethodTransactions: List<PaymentMethodTransactionEntity> = emptyList(),
) = CashRendition(
    createdBy = createdBy,
    shift = shift,
    location = location,
    salesData =
        SalesData(
            invoiceSales = invoiceSales.map(InvoiceSaleEntity::toDomain),
            deliverySales = deliverySales.map(DeliverySaleEntity::toDomain),
            bigBoxSales = bigBoxSales.map(BigBoxSaleEntity::toDomain),
            creditNotes = creditNotes.map(CreditNoteEntity::toDomain),
            initialBalance = initialBalance,
            marketing = marketing,
            currentAccount = currentAccount,
        ),
    additionalData =
        AdditionalData(
            diningRoomData = DiningRoomData(otoshis = salonOtoshis),
            deliveryOsakaData = DeliveryOsakaData(ohashis = delyOskOhashis, orders = delyOskOrders),
            deliveryNoriTacoData = DeliveryNoriTacoData(orders = delyNtOrders, ohashis = delyNtOhashis),
        ),
    reliefs = reliefs.map(ReliefEntity::toDomain),
    paymentMethodTransactions = paymentMethodTransactions.map(PaymentMethodTransactionEntity::toDomain),
    shiftDate = shiftDate,
    id = id!!,
)
