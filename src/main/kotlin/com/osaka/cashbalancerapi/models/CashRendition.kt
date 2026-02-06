package com.osaka.cashbalancerapi.models

import com.osaka.cashbalancerapi.enums.Currency
import com.osaka.cashbalancerapi.enums.Location
import com.osaka.cashbalancerapi.enums.Shift
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

/**
 * Rendimiento de Caja
 * Representa el registro de ventas y movimientos de caja para un turno específico
 */
data class CashRendition(
    val createdBy: User,
    /** Turno (AM/PM) */
    val shift: Shift,
    /** Local donde se realizó el rendimiento */
    val location: Location,
    /** Datos de ventas del turno */
    val salesData: SalesData = SalesData(),
    /** Tasas de cambio para este rendimiento (solo si hay alivios en moneda extranjera) */
    val exchangeRates: ExchangeRates = ExchangeRates(),
    /** Lista de alivios realizados durante el turno */
    val reliefs: List<Relief> = emptyList(),
    /** Lista de transacciones por medio de pago */
    val paymentMethodTransactions: List<PaymentMethodTransaction> = emptyList(),
    /** Fecha del turno que se está rindiendo */
    val shiftDate: LocalDate = LocalDate.now(),
    val id: UUID = UUID.randomUUID(),
) {
    /**
     * Calcula el total de todos los medios de pago
     */
    fun totalPaymentMethods(): BigDecimal =
        paymentMethodTransactions
            .map(PaymentMethodTransaction::amount)
            .fold(BigDecimal.ZERO) { acc, amount -> acc.add(amount) }

    /**
     * Calcula el total de alivios en pesos argentinos
     * Usa las tasas de cambio del rendimiento (exchangeRates)
     */
    fun totalReliefsInArs(): BigDecimal =
        reliefs.fold(BigDecimal.ZERO) { acc, relief ->
            val rateToArs =
                when (relief.currency) {
                    Currency.ARS -> BigDecimal.ONE
                    Currency.USD -> exchangeRates.usdToArs
                    Currency.BRL -> exchangeRates.brlToArs
                    Currency.EUR -> exchangeRates.eurToArs
                }
            acc.add(relief.amount.multiply(rateToArs))
        }

    /**
     * Calcula el total de efectivo disponible en caja
     * Formula: Total Disponible - Total Métodos de Pago - Cuenta Corriente - Total Delivery Sales
     */
    fun totalCashAvailable(): BigDecimal =
        salesData
            .totalAvailable()
            .subtract(totalPaymentMethods())
            .subtract(salesData.currentAccount)
            .subtract(salesData.totalDeliverySales())

    /**
     * Calcula el efectivo real que debería haber en caja en pesos argentinos
     * Formula: Total de Alivios (en ARS) + Saldo Inicial
     */
    fun totalActualCash(): BigDecimal = totalReliefsInArs().add(salesData.initialBalance)

    /**
     * Calcula la diferencia de caja
     * Formula: Total Efectivo Real - Total Efectivo Disponible
     * Esta diferencia debe ser 0 para que el rendimiento cuadre correctamente
     */
    fun cashDifference(): BigDecimal = totalActualCash().subtract(totalCashAvailable())

    /**
     * Calcula el saldo final de caja
     * Formula: Total Efectivo Real - Total de Alivios (en ARS)
     * Representa el efectivo que queda en caja después de retirar los alivios
     */
    fun finalBalance(): BigDecimal = totalActualCash().subtract(totalReliefsInArs())
}
