package com.osaka.cashbalancerapi.services.interfaces

import com.osaka.cashbalancerapi.enums.CreditNoteType
import com.osaka.cashbalancerapi.enums.Currency
import com.osaka.cashbalancerapi.enums.DeliveryPlatform
import com.osaka.cashbalancerapi.enums.InvoiceType
import com.osaka.cashbalancerapi.enums.Location
import com.osaka.cashbalancerapi.enums.PaymentMethodType
import com.osaka.cashbalancerapi.enums.Shift
import com.osaka.cashbalancerapi.models.CashRendition
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

interface ICashRenditionService {
    /**
     * Crea un rendimiento de caja base (sin sales data ni additional data)
     * Los datos de venta y adicionales se agregan posteriormente mediante endpoints específicos
     * @param userId ID del usuario que crea el rendimiento
     * @param shift Turno (MORNING, AFTERNOON, NIGHT)
     * @param location Local (COLEGIALES, PUERTO_MADERO)
     * @param shiftDate Fecha del turno
     * @return El rendimiento de caja creado
     */
    suspend fun create(
        userId: UUID,
        shift: Shift,
        location: Location,
        shiftDate: LocalDate,
    ): CashRendition

    /**
     * Busca un rendimiento de caja por ID y lanza CashRenditionNotFoundException si no existe
     * @throws CashRenditionNotFoundException si el rendimiento no existe
     */
    suspend fun safeFindById(id: UUID): CashRendition

    /**
     * Agrega o actualiza un alivio al rendimiento de caja
     * Si ya existe un alivio con el mismo número de sobre, se actualiza
     * @param renditionId ID del rendimiento de caja
     * @param envelopeNumber Número del sobre (ingresado manualmente)
     * @param currency Tipo de moneda
     * @param amount Monto del alivio
     * @return El rendimiento actualizado con el alivio agregado o actualizado
     * @throws CashRenditionNotFoundException si el rendimiento no existe
     */
    suspend fun addOrUpdateRelief(
        renditionId: UUID,
        envelopeNumber: String,
        currency: Currency,
        amount: BigDecimal,
    ): CashRendition

    /**
     * Obtiene todos los alivios de un rendimiento
     * @param renditionId ID del rendimiento de caja
     * @return El rendimiento con sus alivios
     * @throws CashRenditionNotFoundException si el rendimiento no existe
     */
    suspend fun getReliefsFromRendition(renditionId: UUID): CashRendition

    /**
     * Agrega o actualiza una nota de crédito al rendimiento de caja
     * Si ya existe una nota de crédito con el mismo tipo, se actualiza
     * @param renditionId ID del rendimiento de caja
     * @param type Tipo de nota de crédito (INFOREST, MANUAL_A, MANUAL_B)
     * @param amount Monto de la nota de crédito
     * @param notes Notas aclaratorias opcionales (máximo 500 caracteres)
     * @return El rendimiento actualizado con la nota de crédito agregada o actualizada
     * @throws CashRenditionNotFoundException si el rendimiento no existe
     */
    suspend fun addOrUpdateCreditNote(
        renditionId: UUID,
        type: CreditNoteType,
        amount: BigDecimal,
        notes: String? = null,
    ): CashRendition

    /**
     * Agrega o actualiza una transacción de medio de pago al rendimiento de caja
     * Si ya existe una transacción con el mismo tipo de medio de pago, se actualiza el monto
     * @param renditionId ID del rendimiento de caja
     * @param paymentMethodType Tipo de medio de pago
     * @param amount Monto de la transacción
     * @return El rendimiento actualizado con la transacción de medio de pago
     * @throws CashRenditionNotFoundException si el rendimiento no existe
     */
    suspend fun addOrUpdatePaymentMethodTransaction(
        renditionId: UUID,
        paymentMethodType: PaymentMethodType,
        amount: BigDecimal,
    ): CashRendition

    /**
     * Agrega o actualiza una venta con factura al rendimiento de caja
     * Si ya existe una venta con el mismo tipo de factura, se actualiza el monto
     * @param renditionId ID del rendimiento de caja
     * @param invoiceType Tipo de factura (A o B)
     * @param amount Monto de la venta
     * @return El rendimiento actualizado con la venta con factura agregada o actualizada
     * @throws CashRenditionNotFoundException si el rendimiento no existe
     */
    suspend fun addOrUpdateInvoiceSale(
        renditionId: UUID,
        invoiceType: InvoiceType,
        amount: BigDecimal,
    ): CashRendition

    /**
     * Agrega o actualiza una venta por delivery al rendimiento de caja
     * Si ya existe una venta con la misma plataforma de delivery, se actualiza el monto
     * @param renditionId ID del rendimiento de caja
     * @param deliveryPlatform Plataforma de delivery (RAPPI, PEDIDOS_YA, NORI_TACO)
     * @param amount Monto de la venta
     * @return El rendimiento actualizado con la venta por delivery agregada o actualizada
     * @throws CashRenditionNotFoundException si el rendimiento no existe
     */
    suspend fun addOrUpdateDeliverySale(
        renditionId: UUID,
        deliveryPlatform: DeliveryPlatform,
        amount: BigDecimal,
    ): CashRendition

    /**
     * Actualiza el saldo inicial del rendimiento de caja
     * @param renditionId ID del rendimiento de caja
     * @param amount Monto del saldo inicial
     * @return El rendimiento actualizado
     * @throws CashRenditionNotFoundException si el rendimiento no existe
     */
    suspend fun updateInitialBalance(
        renditionId: UUID,
        amount: BigDecimal,
    ): CashRendition

    /**
     * Actualiza el monto de marketing (ventas en negro) del rendimiento de caja
     * @param renditionId ID del rendimiento de caja
     * @param amount Monto de marketing
     * @return El rendimiento actualizado
     * @throws CashRenditionNotFoundException si el rendimiento no existe
     */
    suspend fun updateMarketing(
        renditionId: UUID,
        amount: BigDecimal,
    ): CashRendition

    /**
     * Actualiza el monto de cuenta corriente del rendimiento de caja
     * @param renditionId ID del rendimiento de caja
     * @param amount Monto de cuenta corriente
     * @return El rendimiento actualizado
     * @throws CashRenditionNotFoundException si el rendimiento no existe
     */
    suspend fun updateCurrentAccount(
        renditionId: UUID,
        amount: BigDecimal,
    ): CashRendition

    /**
     * Actualiza los datos adicionales del salón/lounge
     * @param renditionId ID del rendimiento de caja
     * @param otoshis Cantidad de cubiertos/personas
     * @return El rendimiento actualizado
     * @throws CashRenditionNotFoundException si el rendimiento no existe
     */
    suspend fun updateLoungeData(
        renditionId: UUID,
        otoshis: UInt,
    ): CashRendition

    /**
     * Actualiza los datos adicionales del delivery Osaka
     * @param renditionId ID del rendimiento de caja
     * @param ohashis Cantidad de comensales
     * @param orders Cantidad de pedidos
     * @return El rendimiento actualizado
     * @throws CashRenditionNotFoundException si el rendimiento no existe
     */
    suspend fun updateDeliveryOsakaData(
        renditionId: UUID,
        ohashis: UInt,
        orders: UInt,
    ): CashRendition

    /**
     * Actualiza los datos adicionales del delivery Nori Taco
     * @param renditionId ID del rendimiento de caja
     * @param ohashis Cantidad de comensales
     * @param orders Cantidad de pedidos
     * @return El rendimiento actualizado
     * @throws CashRenditionNotFoundException si el rendimiento no existe
     */
    suspend fun updateDeliveryNoriTacoData(
        renditionId: UUID,
        ohashis: UInt,
        orders: UInt,
    ): CashRendition
}
