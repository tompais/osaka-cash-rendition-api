package com.osaka.cashbalancerapi.handlers

import com.osaka.cashbalancerapi.extensions.awaitBodyAndValidate
import com.osaka.cashbalancerapi.extensions.getUUIDPathVariable
import com.osaka.cashbalancerapi.extensions.toResponse
import com.osaka.cashbalancerapi.requests.CreateCashRenditionRequest
import com.osaka.cashbalancerapi.requests.CreditNoteRequest
import com.osaka.cashbalancerapi.requests.DeliverySaleRequest
import com.osaka.cashbalancerapi.requests.InvoiceSaleRequest
import com.osaka.cashbalancerapi.requests.PaymentMethodTransactionRequest
import com.osaka.cashbalancerapi.requests.ReliefRequest
import com.osaka.cashbalancerapi.services.interfaces.ICashRenditionService
import com.osaka.cashbalancerapi.utils.constants.keys.PathVariableKey
import jakarta.validation.Validator
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class CashRenditionHandler(
    private val cashRenditionService: ICashRenditionService,
    private val validator: Validator,
) {
    /**
     * Crea un nuevo rendimiento de caja
     * POST /cash-renditions
     */
    suspend fun create(request: ServerRequest): ServerResponse {
        val createRequest = request.awaitBodyAndValidate<CreateCashRenditionRequest>(validator)

        val cashRendition =
            cashRenditionService.create(
                userId = createRequest.userId,
                shift = createRequest.shift,
                location = createRequest.location,
                salesData = createRequest.salesData,
                additionalData = createRequest.additionalData,
                shiftDate = createRequest.shiftDate,
            )

        return ServerResponse
            .status(HttpStatus.CREATED)
            .bodyValueAndAwait(cashRendition.toResponse())
    }

    /**
     * Obtiene un rendimiento de caja por ID
     * GET /cash-renditions/{id}
     */
    suspend fun findById(request: ServerRequest): ServerResponse {
        val id = request.getUUIDPathVariable(PathVariableKey.ID)
        val cashRendition = cashRenditionService.safeFindById(id)
        return ServerResponse.ok().bodyValueAndAwait(cashRendition.toResponse())
    }

    /**
     * Agrega o actualiza un alivio
     * POST /cash-renditions/{id}/reliefs
     */
    suspend fun addOrUpdateRelief(request: ServerRequest): ServerResponse {
        val id = request.getUUIDPathVariable(PathVariableKey.ID)
        val reliefRequest = request.awaitBodyAndValidate<ReliefRequest>(validator)

        val cashRendition =
            cashRenditionService.addOrUpdateRelief(
                renditionId = id,
                envelopeNumber = reliefRequest.envelopeNumber,
                currency = reliefRequest.currency,
                amount = reliefRequest.amount,
            )

        return ServerResponse.ok().bodyValueAndAwait(cashRendition.toResponse())
    }

    /**
     * Obtiene los alivios de un rendimiento
     * GET /cash-renditions/{id}/reliefs
     */
    suspend fun getReliefs(request: ServerRequest): ServerResponse {
        val id = request.getUUIDPathVariable(PathVariableKey.ID)
        val cashRendition = cashRenditionService.getReliefsFromRendition(id)
        return ServerResponse.ok().bodyValueAndAwait(cashRendition.reliefs)
    }

    /**
     * Agrega o actualiza una nota de crédito
     * POST /cash-renditions/{id}/credit-notes
     */
    suspend fun addOrUpdateCreditNote(request: ServerRequest): ServerResponse {
        val id = request.getUUIDPathVariable(PathVariableKey.ID)
        val creditNoteRequest = request.awaitBodyAndValidate<CreditNoteRequest>(validator)

        val cashRendition =
            cashRenditionService.addOrUpdateCreditNote(
                renditionId = id,
                type = creditNoteRequest.type,
                amount = creditNoteRequest.amount,
                notes = creditNoteRequest.notes,
            )

        return ServerResponse.ok().bodyValueAndAwait(cashRendition.toResponse())
    }

    /**
     * Agrega o actualiza una transacción de medio de pago
     * POST /cash-renditions/{id}/payment-methods
     */
    suspend fun addOrUpdatePaymentMethodTransaction(request: ServerRequest): ServerResponse {
        val id = request.getUUIDPathVariable(PathVariableKey.ID)
        val paymentMethodRequest = request.awaitBodyAndValidate<PaymentMethodTransactionRequest>(validator)

        val cashRendition =
            cashRenditionService.addOrUpdatePaymentMethodTransaction(
                renditionId = id,
                paymentMethodType = paymentMethodRequest.paymentMethodType,
                amount = paymentMethodRequest.amount,
            )

        return ServerResponse.ok().bodyValueAndAwait(cashRendition.toResponse())
    }

    /**
     * Agrega o actualiza una venta con factura
     * POST /cash-renditions/{id}/invoice-sales
     */
    suspend fun addOrUpdateInvoiceSale(request: ServerRequest): ServerResponse {
        val id = request.getUUIDPathVariable(PathVariableKey.ID)
        val invoiceSaleRequest = request.awaitBodyAndValidate<InvoiceSaleRequest>(validator)

        val cashRendition =
            cashRenditionService.addOrUpdateInvoiceSale(
                renditionId = id,
                invoiceType = invoiceSaleRequest.invoiceType,
                amount = invoiceSaleRequest.amount,
            )

        return ServerResponse.ok().bodyValueAndAwait(cashRendition.toResponse())
    }

    /**
     * Agrega o actualiza una venta por delivery
     * POST /cash-renditions/{id}/delivery-sales
     */
    suspend fun addOrUpdateDeliverySale(request: ServerRequest): ServerResponse {
        val id = request.getUUIDPathVariable(PathVariableKey.ID)
        val deliverySaleRequest = request.awaitBodyAndValidate<DeliverySaleRequest>(validator)

        val cashRendition =
            cashRenditionService.addOrUpdateDeliverySale(
                renditionId = id,
                deliveryPlatform = deliverySaleRequest.platform,
                amount = deliverySaleRequest.amount,
            )

        return ServerResponse.ok().bodyValueAndAwait(cashRendition.toResponse())
    }
}
