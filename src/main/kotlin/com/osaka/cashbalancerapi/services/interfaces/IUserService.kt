package com.osaka.cashbalancerapi.services.interfaces

import com.osaka.cashbalancerapi.models.User
import com.osaka.cashbalancerapi.postgresql.r2dbc.entities.UserEntity
import java.util.UUID

interface IUserService {
    /**
     * Busca un usuario por DNI
     */
    suspend fun findByDni(dni: UInt): User?

    /**
     * Busca un usuario por ID y lanza UserNotFoundException si no existe
     * @throws UserNotFoundException si el usuario no existe
     */
    suspend fun safeFindById(id: UUID): User

    /**
     * Busca una entidad de usuario por ID y lanza UserNotFoundException si no existe
     * @throws UserNotFoundException si el usuario no existe
     */
    suspend fun safeFindEntityById(id: UUID): UserEntity

    suspend fun create(
        firstName: String,
        lastName: String,
        dni: UInt,
    ): User
}
