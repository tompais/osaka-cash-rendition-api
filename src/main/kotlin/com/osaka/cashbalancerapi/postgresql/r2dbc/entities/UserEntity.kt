package com.osaka.cashbalancerapi.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("users")
data class UserEntity(
    @Column("first_name")
    val firstName: String,
    @Column("last_name")
    val lastName: String,
    @Column("dni")
    val dni: UInt,
    @Id
    @Column("id")
    val id: UUID = UUID.randomUUID(),
)
