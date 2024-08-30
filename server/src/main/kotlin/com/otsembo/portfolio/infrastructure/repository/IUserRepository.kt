package com.otsembo.portfolio.infrastructure.repository

import com.otsembo.portfolio.domain.models.User
import com.otsembo.portfolio.domain.models.UserDTO

interface IUserRepository {
    suspend fun create(userDTO: UserDTO): User?

    suspend fun find(id: Long): User?

    suspend fun findByUsername(username: String): User?

    suspend fun findPasswordHash(username: String): String?
}
