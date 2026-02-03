package com.osaka.cashbalancerapi.r2dbc.repositories.interfaces

import com.osaka.cashbalancerapi.entities.UserEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface IUserRepository : CoroutineCrudRepository<UserEntity, UUID> {
    suspend fun findByDni(dni: UInt): UserEntity?

    suspend fun existsByDni(dni: UInt): Boolean
}
