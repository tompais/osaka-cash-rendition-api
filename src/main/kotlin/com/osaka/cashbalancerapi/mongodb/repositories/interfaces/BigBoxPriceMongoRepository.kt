package com.osaka.cashbalancerapi.repositories.mongodb

import com.osaka.cashbalancerapi.entities.BigBoxExperienceType
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
interface BigBoxPriceMongoRepository : CoroutineCrudRepository<BigBoxPriceDocument, UUID> {
    /**
     * Busca precios por tipo de experiencia
     */
    fun findByExperienceType(experienceType: BigBoxExperienceType): Flow<BigBoxPriceDocument>

    /**
     * Busca el precio vigente para un tipo de experiencia en una fecha específica
     */
    suspend fun findByExperienceTypeAndValidFromLessThanEqualAndValidUntilGreaterThanEqual(
        experienceType: BigBoxExperienceType,
        validFrom: LocalDateTime,
        validUntil: LocalDateTime,
    ): BigBoxPriceDocument?

    /**
     * Busca precios para un tipo de experiencia donde validFrom <= date
     */
    fun findByExperienceTypeAndValidFromLessThanEqual(
        experienceType: BigBoxExperienceType,
        date: LocalDateTime,
    ): Flow<BigBoxPriceDocument>
}
