package com.osaka.cashbalancerapi.repositories.mongodb

import com.osaka.cashbalancerapi.entities.BigBoxExperienceType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Document(collection = "bigbox_prices")
data class BigBoxPriceDocument(
    @Indexed
    val experienceType: BigBoxExperienceType,
    val unitPrice: BigDecimal,
    @Indexed
    val validFrom: LocalDateTime,
    val validUntil: LocalDateTime? = null,
    @Id
    val id: UUID = UUID.randomUUID(),
)
