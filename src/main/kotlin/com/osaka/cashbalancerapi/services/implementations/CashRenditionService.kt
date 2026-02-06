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
import com.osaka.cashbalancerapi.models.CashRendition
import com.osaka.cashbalancerapi.models.CreditNote
import com.osaka.cashbalancerapi.models.DeliverySale
import com.osaka.cashbalancerapi.models.InvoiceSale
import com.osaka.cashbalancerapi.models.PaymentMethodTransaction
import com.osaka.cashbalancerapi.models.Relief
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
import com.osaka.cashbalancerapi.services.interfaces.IUserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
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
) : ICashRenditionService {
    override suspend fun create(
        userId: UUID,
        shift: Shift,
        location: Location,
        shiftDate: LocalDate,
    ): CashRendition =
        withContext(Dispatchers.IO) {
            // Obtener usuario (lanza excepción si no existe)
            val user = safeFindUserById(userId)

            // Crear CashRendition base (sin sales data completo, solo valores por defecto)
            val cashRendition =
                CashRendition(
                    createdBy = user,
                    shift = shift,
                    location = location,
                    shiftDate = shiftDate,
                )

            // Guardar el CashRenditionEntity
            val savedEntity = saveCashRenditionEntity(cashRendition.toEntity())

            // Retornar el rendimiento creado (sin relaciones adicionales, ya que no hay)
            safeFindById(savedEntity.id!!)
        }

    /**
     * Carga todas las relaciones de un CashRenditionEntity desde los repositorios
     * Utiliza coroutines para cargar todas las relaciones en paralelo mejorando el rendimiento
     */
    private suspend fun loadCashRenditionWithRelations(
        entity: CashRenditionEntity,
        user: User,
    ): CashRendition =
        withContext(Dispatchers.IO) {
            // Iniciar todas las consultas en paralelo usando async
            val reliefsDeferred = async { reliefRepository.findAllByCashRenditionId(entity.id!!).toList() }
            val invoiceSalesDeferred =
                async { invoiceSaleRepository.findAllByCashRenditionId(entity.id!!).toList() }
            val deliverySalesDeferred =
                async { deliverySaleRepository.findAllByCashRenditionId(entity.id!!).toList() }
            val bigBoxSalesDeferred = async { bigBoxSaleRepository.findAllByCashRenditionId(entity.id!!).toList() }
            val creditNotesDeferred = async { creditNoteRepository.findAllByCashRenditionId(entity.id!!).toList() }
            val paymentMethodTransactionsDeferred =
                async { paymentMethodTransactionRepository.findAllByCashRenditionId(entity.id!!).toList() }

            // Esperar a que todas las consultas se completen
            entity.toDomain(
                createdBy = user,
                reliefs = reliefsDeferred.await(),
                invoiceSales = invoiceSalesDeferred.await(),
                deliverySales = deliverySalesDeferred.await(),
                bigBoxSales = bigBoxSalesDeferred.await(),
                creditNotes = creditNotesDeferred.await(),
                paymentMethodTransactions = paymentMethodTransactionsDeferred.await(),
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
    ): CashRendition =
        withContext(Dispatchers.IO) {
            // Ejecutar verificaciones en paralelo
            val renditionDeferred = async { safeFindById(renditionId) }
            val existingReliefDeferred =
                async { reliefRepository.findByEnvelopeNumberAndCashRenditionId(envelopeNumber, renditionId) }

            // Esperar a que las verificaciones se completen
            renditionDeferred.await()
            val existingRelief = existingReliefDeferred.await()

            val relief =
                Relief(
                    envelopeNumber = envelopeNumber,
                    amount = amount,
                    currency = currency,
                )

            if (existingRelief != null) {
                // Si existe, actualizar manteniendo el ID
                reliefRepository.save(relief.toEntity(renditionId).copy(id = existingRelief.id))
            } else {
                // Si no existe, crear nuevo
                reliefRepository.save(relief.toEntity(renditionId))
            }

            // Retornar el rendimiento actualizado con todas las relaciones
            safeFindById(renditionId)
        }

    override suspend fun getReliefsFromRendition(renditionId: UUID): CashRendition = safeFindById(renditionId)

    override suspend fun addOrUpdateCreditNote(
        renditionId: UUID,
        type: CreditNoteType,
        amount: BigDecimal,
        notes: String?,
    ): CashRendition =
        withContext(Dispatchers.IO) {
            // Ejecutar verificaciones en paralelo
            val renditionDeferred = async { safeFindById(renditionId) }
            val existingCreditNoteDeferred =
                async { creditNoteRepository.findByTypeAndCashRenditionId(type, renditionId) }

            // Esperar a que las verificaciones se completen
            renditionDeferred.await()
            val existingCreditNote = existingCreditNoteDeferred.await()

            val creditNote = CreditNote(type, amount, notes)

            if (existingCreditNote != null) {
                // Si existe, actualizar manteniendo el ID
                creditNoteRepository.save(creditNote.toEntity(renditionId).copy(id = existingCreditNote.id))
            } else {
                // Si no existe, crear nuevo
                creditNoteRepository.save(creditNote.toEntity(renditionId))
            }

            // Retornar el rendimiento actualizado
            safeFindById(renditionId)
        }

    override suspend fun addOrUpdatePaymentMethodTransaction(
        renditionId: UUID,
        paymentMethodType: PaymentMethodType,
        amount: BigDecimal,
    ): CashRendition =
        withContext(Dispatchers.IO) {
            // Ejecutar verificaciones en paralelo
            val renditionDeferred = async { safeFindById(renditionId) }
            val existingTransactionDeferred =
                async {
                    paymentMethodTransactionRepository.findByPaymentMethodTypeAndCashRenditionId(
                        paymentMethodType,
                        renditionId,
                    )
                }

            // Esperar a que las verificaciones se completen
            renditionDeferred.await()
            val existingTransaction = existingTransactionDeferred.await()

            val transaction = PaymentMethodTransaction(paymentMethodType, amount)

            if (existingTransaction != null) {
                // Si existe, actualizar manteniendo el ID
                paymentMethodTransactionRepository.save(
                    transaction.toEntity(renditionId).copy(id = existingTransaction.id),
                )
            } else {
                // Si no existe, crear nuevo
                paymentMethodTransactionRepository.save(transaction.toEntity(renditionId))
            }

            // Retornar el rendimiento actualizado
            safeFindById(renditionId)
        }

    override suspend fun addOrUpdateInvoiceSale(
        renditionId: UUID,
        invoiceType: InvoiceType,
        amount: BigDecimal,
    ): CashRendition =
        withContext(Dispatchers.IO) {
            // Ejecutar verificaciones en paralelo
            val renditionDeferred = async { safeFindById(renditionId) }
            val existingInvoiceSaleDeferred =
                async { invoiceSaleRepository.findByTypeAndCashRenditionId(invoiceType, renditionId) }

            // Esperar a que las verificaciones se completen
            renditionDeferred.await()
            val existingInvoiceSale = existingInvoiceSaleDeferred.await()

            val invoiceSale = InvoiceSale(invoiceType, amount)

            if (existingInvoiceSale != null) {
                // Si existe, actualizar manteniendo el ID
                invoiceSaleRepository.save(invoiceSale.toEntity(renditionId).copy(id = existingInvoiceSale.id))
            } else {
                // Si no existe, crear nuevo
                invoiceSaleRepository.save(invoiceSale.toEntity(renditionId))
            }

            // Retornar el rendimiento actualizado
            safeFindById(renditionId)
        }

    override suspend fun addOrUpdateDeliverySale(
        renditionId: UUID,
        deliveryPlatform: DeliveryPlatform,
        amount: BigDecimal,
    ): CashRendition =
        withContext(Dispatchers.IO) {
            // Ejecutar verificaciones en paralelo
            val renditionDeferred = async { safeFindById(renditionId) }
            val existingDeliverySaleDeferred =
                async { deliverySaleRepository.findByPlatformAndCashRenditionId(deliveryPlatform, renditionId) }

            // Esperar a que las verificaciones se completen
            renditionDeferred.await()
            val existingDeliverySale = existingDeliverySaleDeferred.await()

            val deliverySale = DeliverySale(deliveryPlatform, amount)

            if (existingDeliverySale != null) {
                // Si existe, actualizar manteniendo el ID
                deliverySaleRepository.save(deliverySale.toEntity(renditionId).copy(id = existingDeliverySale.id))
            } else {
                // Si no existe, crear nuevo
                deliverySaleRepository.save(deliverySale.toEntity(renditionId))
            }

            // Retornar el rendimiento actualizado
            safeFindById(renditionId)
        }

    private suspend fun saveCashRenditionEntity(cashRenditionEntity: CashRenditionEntity): CashRenditionEntity =
        cashRenditionRepository.save(cashRenditionEntity)

    private suspend fun findCashRenditionEntityById(id: UUID): CashRenditionEntity? = cashRenditionRepository.findById(id)

    private suspend fun safeFindUserById(userId: UUID): User = userService.safeFindById(userId)

    override suspend fun updateInitialBalance(
        renditionId: UUID,
        amount: BigDecimal,
    ): CashRendition =
        withContext(Dispatchers.IO) {
            val entity = findCashRenditionEntityById(renditionId) ?: throw CashRenditionNotFoundException(renditionId)
            cashRenditionRepository.save(entity.copy(initialBalance = amount))
            safeFindById(renditionId)
        }

    override suspend fun updateMarketing(
        renditionId: UUID,
        amount: BigDecimal,
    ): CashRendition =
        withContext(Dispatchers.IO) {
            val entity = findCashRenditionEntityById(renditionId) ?: throw CashRenditionNotFoundException(renditionId)
            cashRenditionRepository.save(entity.copy(marketing = amount))
            safeFindById(renditionId)
        }

    override suspend fun updateCurrentAccount(
        renditionId: UUID,
        amount: BigDecimal,
    ): CashRendition =
        withContext(Dispatchers.IO) {
            val entity = findCashRenditionEntityById(renditionId) ?: throw CashRenditionNotFoundException(renditionId)
            cashRenditionRepository.save(entity.copy(currentAccount = amount))
            safeFindById(renditionId)
        }

    override suspend fun updateLoungeData(
        renditionId: UUID,
        otoshis: UInt,
        ohashis: UInt,
    ): CashRendition =
        withContext(Dispatchers.IO) {
            val entity = findCashRenditionEntityById(renditionId) ?: throw CashRenditionNotFoundException(renditionId)
            cashRenditionRepository.save(
                entity.copy(
                    loungeOtoshis = otoshis,
                    loungeOhashis = ohashis,
                ),
            )
            safeFindById(renditionId)
        }

    override suspend fun updateOsakaOrders(
        renditionId: UUID,
        ohashis: UInt,
        orders: UInt,
    ): CashRendition =
        withContext(Dispatchers.IO) {
            val entity = findCashRenditionEntityById(renditionId) ?: throw CashRenditionNotFoundException(renditionId)
            cashRenditionRepository.save(
                entity.copy(
                    osakaOhashis = ohashis,
                    osakaOrders = orders,
                ),
            )
            safeFindById(renditionId)
        }

    override suspend fun updateNoriTacoOrders(
        renditionId: UUID,
        ohashis: UInt,
        orders: UInt,
    ): CashRendition =
        withContext(Dispatchers.IO) {
            val entity = findCashRenditionEntityById(renditionId) ?: throw CashRenditionNotFoundException(renditionId)
            cashRenditionRepository.save(
                entity.copy(
                    noriTacoOhashis = ohashis,
                    noriTacoOrders = orders,
                ),
            )
            safeFindById(renditionId)
        }
}
