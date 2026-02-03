package com.osaka.cashbalancerapi.enums

/**
 * Tipos de notas de crédito que anulan diferentes tipos de ventas
 */
enum class CreditNoteType {
    /** Cancela ventas de Marketing (ventas en negro) */
    INFOREST,

    /** Cancela Factura A */
    MANUAL_A,

    /** Cancela Factura B */
    MANUAL_B,
}
