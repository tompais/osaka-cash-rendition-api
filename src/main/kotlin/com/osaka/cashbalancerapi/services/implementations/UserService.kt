package com.osaka.cashbalancerapi.services.implementations

import com.osaka.cashbalancerapi.errors.exceptions.UserNotFoundException
import com.osaka.cashbalancerapi.extensions.toDomain
import com.osaka.cashbalancerapi.extensions.toEntity
import com.osaka.cashbalancerapi.models.User
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.UserEntity
import com.osaka.cashbalancerapi.postgresql.r2dbc.repositories.interfaces.IUserRepository
import com.osaka.cashbalancerapi.services.interfaces.IUserService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRepository: IUserRepository,
) : IUserService {
    override suspend fun findByDni(dni: UInt): User? = userRepository.findByDni(dni)?.toDomain()

    override suspend fun safeFindById(id: UUID): User = safeFindEntityById(id).toDomain()

    override suspend fun safeFindEntityById(id: UUID): UserEntity = userRepository.findById(id) ?: throw UserNotFoundException(id)

    override suspend fun create(
        firstName: String,
        lastName: String,
        dni: UInt,
    ): User = userRepository.save(User(firstName, lastName, dni).toEntity()).toDomain()
}
