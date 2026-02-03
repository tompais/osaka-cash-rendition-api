package com.osaka.cashbalancerapi.repositories.mongodb

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserMongoRepository : CoroutineCrudRepository<UserDocument, UUID> {
    suspend fun findByDni(dni: UInt): UserDocument?
}
