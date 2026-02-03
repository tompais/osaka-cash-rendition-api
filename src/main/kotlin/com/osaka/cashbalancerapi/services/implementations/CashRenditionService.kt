package com.osaka.cashbalancerapi.services.implementations

import com.osaka.cashbalancerapi.enums.CreditNoteType
import com.osaka.cashbalancerapi.enums.Currency
import com.osaka.cashbalancerapi.enums.DeliveryPlatform
import com.osaka.cashbalancerapi.enums.InvoiceType
import com.osaka.cashbalancerapi.enums.Location
import com.osaka.cashbalancerapi.enums.PaymentMethodType
import com.osaka.cashbalancerapi.enums.Shift
import com.osaka.cashbalancerapi.errors.exceptions.CashRenditionNotFoundException
import com.osaka.cashbalancerapi.extensions.toDomain
import com.osaka.cashbalancerapi.extensions.toEntity
import com.osaka.cashbalancerapi.models.AdditionalData
import com.osaka.cashbalancerapi.models.CashRendition
import com.osaka.cashbalancerapi.models.CreditNote
import com.osaka.cashbalancerapi.models.DeliverySale
import com.osaka.cashbalancerapi.models.ExchangeRate
import com.osaka.cashbalancerapi.models.InvoiceSale
import com.osaka.cashbalancerapi.models.PaymentMethodTransaction
import com.osaka.cashbalancerapi.models.Relief
import com.osaka.cashbalancerapi.models.SalesData
import com.osaka.cashbalancerapi.models.User
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.CashRenditionEntity
import com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces.IBigBoxSaleRepository
import com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces.ICashRenditionRepository
import com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces.ICreditNoteRepository
import com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces.IDeliverySaleRepository
import com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces.IInvoiceSaleRepository
import com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces.IPaymentMethodTransactionRepository
import com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces.IReliefRepository
import com.osaka.cashbalancerapi.services.interfaces.ICashRenditionService
import com.osaka.cashbalancerapi.services.interfaces.IExchangeRateService
import com.osaka.cashbalancerapi.services.interfaces.IUserService
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Service
class CashRenditionService(
    private val cashRenditionRepository: ICashRenditionRepository,
    private val reliefRepository: IReliefRepository,
    private val invoiceSaleRepository: IInvoiceSaleRepository,
    private val deliverySaleRepository: IDeliverySaleRepository,
    private val bigBoxSaleRepository: IBigBoxSaleRepository,
    private val creditNoteRepository: ICreditNoteRepository,
    private val paymentMethodTransactionRepository: IPaymentMethodTransactionRepository,
    private val userService: IUserService,
    private val exchangeRateService: IExchangeRateService,
) : ICashRenditionService {
    override suspend fun create(
        userId: UUID,
        shift: Shift,
        location: Location,
        salesData: SalesData,
        additionalData: AdditionalData,
        shiftDate: LocalDate,
    ): CashRendition {
        // Obtener usuario (lanza excepción si no existe)
        val user = safeFindUserById(userId)

        // Crear CashRendition en memoria
        val cashRendition =
            CashRendition(
                user,
                shift,
                location,
                salesData,
                additionalData,
                shiftDate = shiftDate,
            )

        // 1. Guardar el CashRenditionEntity primero (SIN las relaciones)
        val savedEntity = saveCashRenditionEntity(cashRendition.toEntity())

        // 2. Guardar las entidades hijas con el cashRenditionId
        // Guardar reliefs
        cashRendition.reliefs.forEach { relief ->
            reliefRepository.save(relief.toEntity(savedEntity.id!!))
        }

        // Guardar invoice sales
        salesData.invoiceSales.forEach { invoiceSale ->
            invoiceSaleRepository.save(invoiceSale.toEntity(savedEntity.id!!))
        }

        // Guardar delivery sales
        salesData.deliverySales.forEach { deliverySale ->
            deliverySaleRepository.save(deliverySale.toEntity(savedEntity.id!!))
        }

        // Guardar big box sales
        salesData.bigBoxSales.forEach { bigBoxSale ->
            bigBoxSaleRepository.save(bigBoxSale.toEntity(savedEntity.id!!))
        }

        // Guardar credit notes
        salesData.creditNotes.forEach { creditNote ->
            creditNoteRepository.save(creditNote.toEntity(savedEntity.id!!))
        }

        // Guardar payment method transactions
        cashRendition.paymentMethodTransactions.forEach { transaction ->
            paymentMethodTransactionRepository.save(transaction.toEntity(savedEntity.id!!))
        }

        // 3. Recargar con todas las relaciones
        return safeFindById(savedEntity.id!!)
    }

    /**
     * Carga todas las relaciones de un CashRenditionEntity desde los repositorios
     */
    private suspend fun loadCashRenditionWithRelations(
        entity: CashRenditionEntity,
        user: User,
    ): CashRendition {
        val reliefs = reliefRepository.findAllByCashRenditionId(entity.id!!)
        val invoiceSales = invoiceSaleRepository.findAllByCashRenditionId(entity.id)
        val deliverySales = deliverySaleRepository.findAllByCashRenditionId(entity.id)
        val bigBoxSales = bigBoxSaleRepository.findAllByCashRenditionId(entity.id)
        val creditNotes = creditNoteRepository.findAllByCashRenditionId(entity.id)
        val paymentMethodTransactions = paymentMethodTransactionRepository.findAllByCashRenditionId(entity.id)

        return entity.toDomain(
            createdBy = user,
            reliefs = reliefs,
            invoiceSales = invoiceSales,
            deliverySales = deliverySales,
            bigBoxSales = bigBoxSales,
            creditNotes = creditNotes,
            paymentMethodTransactions = paymentMethodTransactions,
        )
    }

    private suspend fun findById(id: UUID): CashRendition? =
        findCashRenditionEntityById(id)?.let { cashRenditionEntity ->
            loadCashRenditionWithRelations(cashRenditionEntity, safeFindUserById(cashRenditionEntity.createdByUserId))
        }

    override suspend fun safeFindById(id: UUID): CashRendition = findById(id) ?: throw CashRenditionNotFoundException(id)

    override suspend fun addOrUpdateRelief(
        renditionId: UUID,
        envelopeNumber: String,
        currency: Currency,
        amount: BigDecimal,
    ): CashRendition {
        // Verificar que el rendimiento existe
        safeFindById(renditionId)

        // Buscar si ya existe un alivio con el mismo número de sobre
        val existingRelief = reliefRepository.findByEnvelopeNumberAndCashRenditionId(envelopeNumber, renditionId)

        val relief = createRelief(envelopeNumber, currency, amount)

        if (existingRelief != null) {
            // Si existe, actualizar manteniendo el ID
            reliefRepository.save(relief.toEntity(renditionId).copy(id = existingRelief.id))
        } else {
            // Si no existe, crear nuevo
            reliefRepository.save(relief.toEntity(renditionId))
        }

        // Retornar el rendimiento actualizado con todas las relaciones
        return safeFindById(renditionId)
    }

    private suspend fun createRelief(
        envelopeNumber: String,
        currency: Currency,
        amount: BigDecimal,
    ): Relief =
        findExchangeRateByCurrency(currency).let { exchangeRate ->
            Relief.create(
                envelopeNumber,
                amount,
                currency,
                exchangeRate.rateToArs,
                exchangeRate.id,
            )
        }

    private suspend fun findExchangeRateByCurrency(currency: Currency): ExchangeRate =
        exchangeRateService.findActiveRateByCurrency(currency)

    override suspend fun getReliefsFromRendition(renditionId: UUID): CashRendition = safeFindById(renditionId)

    override suspend fun addOrUpdateCreditNote(
        renditionId: UUID,
        type: CreditNoteType,
        amount: BigDecimal,
        notes: String?,
    ): CashRendition {
        // Verificar que el rendimiento existe
        safeFindById(renditionId)

        // Buscar si ya existe una nota de crédito con el mismo tipo
        val existingCreditNote = creditNoteRepository.findByTypeAndCashRenditionId(type, renditionId)

        val creditNote = CreditNote(type, amount, notes)

        if (existingCreditNote != null) {
            // Si existe, actualizar manteniendo el ID
            creditNoteRepository.save(creditNote.toEntity(renditionId).copy(id = existingCreditNote.id))
        } else {
            // Si no existe, crear nuevo
            creditNoteRepository.save(creditNote.toEntity(renditionId))
        }

        // Retornar el rendimiento actualizado
        return safeFindById(renditionId)
    }

    override suspend fun addOrUpdatePaymentMethodTransaction(
        renditionId: UUID,
        paymentMethodType: PaymentMethodType,
        amount: BigDecimal,
    ): CashRendition {
        // Verificar que el rendimiento existe
        safeFindById(renditionId)

        // Buscar si ya existe una transacción con el mismo tipo de medio de pago
        val existingTransaction =
            paymentMethodTransactionRepository.findByPaymentMethodTypeAndCashRenditionId(paymentMethodType, renditionId)

        val transaction = PaymentMethodTransaction(paymentMethodType, amount)

        if (existingTransaction != null) {
            // Si existe, actualizar manteniendo el ID
            paymentMethodTransactionRepository.save(transaction.toEntity(renditionId).copy(id = existingTransaction.id))
        } else {
            // Si no existe, crear nuevo
            paymentMethodTransactionRepository.save(transaction.toEntity(renditionId))
        }

        // Retornar el rendimiento actualizado
        return safeFindById(renditionId)
    }

    override suspend fun addOrUpdateInvoiceSale(
        renditionId: UUID,
        invoiceType: InvoiceType,
        amount: BigDecimal,
    ): CashRendition {
        // Verificar que el rendimiento existe
        safeFindById(renditionId)

        // Buscar si ya existe una venta con factura del mismo tipo
        val existingInvoiceSale = invoiceSaleRepository.findByTypeAndCashRenditionId(invoiceType, renditionId)

        val invoiceSale = InvoiceSale(invoiceType, amount)

        if (existingInvoiceSale != null) {
            // Si existe, actualizar manteniendo el ID
            invoiceSaleRepository.save(invoiceSale.toEntity(renditionId).copy(id = existingInvoiceSale.id))
        } else {
            // Si no existe, crear nuevo
            invoiceSaleRepository.save(invoiceSale.toEntity(renditionId))
        }

        // Retornar el rendimiento actualizado
        return safeFindById(renditionId)
    }

    override suspend fun addOrUpdateDeliverySale(
        renditionId: UUID,
        deliveryPlatform: DeliveryPlatform,
        amount: BigDecimal,
    ): CashRendition {
        // Verificar que el rendimiento existe
        safeFindById(renditionId)

        // Buscar si ya existe una venta por delivery de la misma plataforma
        val existingDeliverySale =
            deliverySaleRepository.findByPlatformAndCashRenditionId(deliveryPlatform, renditionId)

        val deliverySale = DeliverySale(deliveryPlatform, amount)

        if (existingDeliverySale != null) {
            // Si existe, actualizar manteniendo el ID
            deliverySaleRepository.save(deliverySale.toEntity(renditionId).copy(id = existingDeliverySale.id))
        } else {
            // Si no existe, crear nuevo
            deliverySaleRepository.save(deliverySale.toEntity(renditionId))
        }

        // Retornar el rendimiento actualizado
        return safeFindById(renditionId)
    }

    private suspend fun saveCashRenditionEntity(cashRenditionEntity: CashRenditionEntity): CashRenditionEntity =
        cashRenditionRepository.save(cashRenditionEntity)

    private suspend fun findCashRenditionEntityById(id: UUID): CashRenditionEntity? = cashRenditionRepository.findById(id)

    private suspend fun safeFindUserById(userId: UUID): User = userService.safeFindById(userId)
}
