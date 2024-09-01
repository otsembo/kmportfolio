package com.otsembo.portfolio.infrastructure.repository

import com.otsembo.portfolio.domain.mappers.AuthException
import com.otsembo.portfolio.domain.models.User
import com.otsembo.portfolio.domain.models.UserDTO

interface IAuthRepository {
    suspend fun signIn(info: UserDTO): User?

    suspend fun signOut(token: String): Boolean

    suspend fun createAccount(info: UserDTO): User?
}

inline fun requireAuth(
    value: Boolean,
    lazyMessage: () -> Any,
) {
    if (!value) {
        val message = lazyMessage()
        throw AuthException(message.toString())
    }
}
