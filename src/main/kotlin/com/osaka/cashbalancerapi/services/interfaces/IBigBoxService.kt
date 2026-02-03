package com.osaka.cashbalancerapi.services.interfaces

import com.osaka.cashbalancerapi.enums.ExperienceType
import com.osaka.cashbalancerapi.models.BigBox
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

interface IBigBoxService {
    /**
     * Crea un nuevo precio para un tipo de experiencia BigBox
     */
    suspend fun create(
        experienceType: ExperienceType,
        unitPrice: BigDecimal,
    ): BigBox

    /**
     * Obtiene el precio activo para un tipo de experiencia
     */
    suspend fun getActiveByExperienceType(experienceType: ExperienceType): BigBox?

    /**
     * Obtiene todos los BigBox
     */
    fun getAll(): Flow<BigBox>

    /**
     * Obtiene un BigBox por ID
     */
    suspend fun getById(id: UUID): BigBox?

    /**
     * Actualiza la fecha de finalización de validez de un BigBox
     */
    suspend fun updateValidUntil(
        id: UUID,
        validUntil: LocalDateTime,
    ): BigBox?
}
