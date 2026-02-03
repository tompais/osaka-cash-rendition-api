package com.osaka.cashbalancerapi.repositories.mongodb

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "users")
data class UserDocument(
    val firstName: String,
    val lastName: String,
    @Indexed(unique = true)
    val dni: UInt,
    @Id
    val id: UUID = UUID.randomUUID(),
)
