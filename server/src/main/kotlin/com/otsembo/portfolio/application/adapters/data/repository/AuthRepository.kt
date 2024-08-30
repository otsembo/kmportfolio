package com.otsembo.portfolio.application.adapters.data.repository

import com.otsembo.portfolio.domain.models.User
import com.otsembo.portfolio.domain.models.UserDTO
import com.otsembo.portfolio.domain.services.JWTService
import com.otsembo.portfolio.domain.services.PasswordService
import com.otsembo.portfolio.infrastructure.repository.IAuthRepository
import com.otsembo.portfolio.infrastructure.repository.IUserRepository
import com.otsembo.portfolio.infrastructure.repository.requireAuth

class AuthRepository(
    private val userRepository: IUserRepository,
    private val jwtService: JWTService,
    private val passwordService: PasswordService,
) : IAuthRepository {
    override suspend fun signIn(info: UserDTO): User? {
        requireAuth(info.username.isNotBlank()) { "Username cannot be blank" }
        requireAuth(info.password.isNotBlank()) { "Password cannot be blank" }
        val passHash = userRepository.findPasswordHash(info.username)
        requireAuth(passHash != null) { "User does not exist" }
        requireAuth(passwordService.verifyPassword(info.password, info.password)) {
            "Password is incorrect"
        }
        val token = jwtService.createToken(info.username)
        return userRepository.findByUsername(info.username)?.copy(token = token)
    }

    override suspend fun signOut(token: String): Boolean {
        // Revoke token credentials
        return true
    }

    override suspend fun createAccount(info: UserDTO): User? {
        requireAuth(info.username.length < 4) { "Username cannot be less than 4 characters" }
        requireAuth(passwordService.isPasswordValid(info.password)) {
            "Password is not valid. It must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter and one digit."
        }
        val hashedPassword = passwordService.hashPassword(info.password)
        val user = userRepository.create(info.copy(password = hashedPassword))
        val token = jwtService.createToken(info.username)
        return user?.copy(token = token)
    }
}
