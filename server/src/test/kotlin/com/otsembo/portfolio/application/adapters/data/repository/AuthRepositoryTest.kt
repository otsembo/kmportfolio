package com.otsembo.portfolio.application.adapters.data.repository

import com.otsembo.portfolio.domain.mappers.AuthException
import com.otsembo.portfolio.domain.models.User
import com.otsembo.portfolio.domain.models.UserDTO
import com.otsembo.portfolio.domain.services.JWTService
import com.otsembo.portfolio.domain.services.PasswordService
import com.otsembo.portfolio.infrastructure.repository.IUserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class AuthRepositoryTest {
    private val userRepository = mockk<IUserRepository>()
    private val jwtService = mockk<JWTService>()
    private val passwordService = mockk<PasswordService>()
    private val authRepository = AuthRepository(userRepository, jwtService, passwordService)
    private val userDTO = UserDTO("test@user", "testUser", "password123")
    private val user = User(1, "test@user", "testUser")

    @Test
    fun `signIn with valid_credentials_returns_user_with_token`() =
        runTest {
            val hashedPassword = "hashedPassword"
            val token = "testToken"

            coEvery { userRepository.findPasswordHash(userDTO.username) } returns hashedPassword
            coEvery { passwordService.verifyPassword(userDTO.password, hashedPassword) } returns true
            coEvery { jwtService.createToken(userDTO.username) } returns token
            coEvery { userRepository.findByUsername(userDTO.username) } returns user

            val result = authRepository.signIn(userDTO)

            coVerify(exactly = 1) {
                userRepository.findPasswordHash(userDTO.username)
                passwordService.verifyPassword(userDTO.password, hashedPassword)
                jwtService.createToken(userDTO.username)
                userRepository.findByUsername(userDTO.username)
            }
            assertEquals(user.copy(token = token), result)
        }

    @Test
    fun `signIn with blank username throws exception`() =
        runTest {
            coEvery { userRepository.findPasswordHash(userDTO.username) } returns null
            assertFailsWith<AuthException> {
                authRepository.signIn(userDTO)
            }
        }

    @Test
    fun `signIn with blank password throws exception`() =
        runTest {
            coEvery { userRepository.findPasswordHash(userDTO.username) } returns null
            assertFailsWith<AuthException> {
                authRepository.signIn(userDTO)
            }
        }

    @Test
    fun `signIn with non-existent user throws exception`() =
        runTest {
            coEvery { userRepository.findPasswordHash(userDTO.username) } returns null

            assertFailsWith<AuthException> {
                authRepository.signIn(userDTO)
            }

            coVerify(exactly = 1) { userRepository.findPasswordHash(userDTO.username) }
            coVerify(exactly = 0) { passwordService.verifyPassword(any(), any()) }
        }

    @Test
    fun `signIn with incorrect password throws exception`() =
        runTest {
            val hashedPassword = "hashedPassword"

            coEvery { userRepository.findPasswordHash(userDTO.username) } returns hashedPassword
            coEvery { passwordService.verifyPassword(userDTO.password, hashedPassword) } returns false

            assertFailsWith<AuthException> {
                authRepository.signIn(userDTO)
            }

            coVerify(exactly = 1) {
                userRepository.findPasswordHash(userDTO.username)
                passwordService.verifyPassword(userDTO.password, hashedPassword)
            }
            coVerify(exactly = 0) { jwtService.createToken(any()) }
        }

    @Test
    fun `signOut returns true`() =
        runTest {
            val token = "testToken"

            val result = authRepository.signOut(token)

            assertEquals(true, result)
        }

    @Test
    fun `createAccount with valid info returns user with token`() =
        runTest {
            val hashedPassword = "hashedPassword"
            val token = "testToken"

            every { passwordService.isPasswordValid(userDTO.password) } returns true
            every { passwordService.hashPassword(userDTO.password) } returns hashedPassword
            coEvery { userRepository.create(any()) } returns user
            every { jwtService.createToken(userDTO.username) } returns token

            val result = authRepository.createAccount(userDTO)

            coVerify(exactly = 1) {
                passwordService.isPasswordValid(userDTO.password)
                passwordService.hashPassword(userDTO.password)
                userRepository.create(userDTO.copy(password = hashedPassword))
                jwtService.createToken(userDTO.username)
            }
            assertEquals(user.copy(token = token), result)
        }

    @Test
    fun `createAccount with short username throws exception`() =
        runTest {
            assertFailsWith<AuthException> {
                authRepository.createAccount(userDTO.copy(username = "u"))
            }

            coVerify(exactly = 0) { passwordService.isPasswordValid(any()) }
        }

    @Test
    fun `createAccount with invalid password throws exception`() =
        runTest {
            coEvery { passwordService.isPasswordValid(userDTO.password) } returns false

            assertFailsWith<AuthException> {
                authRepository.createAccount(userDTO)
            }

            coVerify(exactly = 1) { passwordService.isPasswordValid(userDTO.password) }
            coVerify(exactly = 0) { passwordService.hashPassword(any()) }
        }

    @Test
    fun `createAccount with existing username returns null`() =
        runTest {
            val hashedPassword = "hashedPassword"

            coEvery { passwordService.isPasswordValid(userDTO.password) } returns true
            coEvery { passwordService.hashPassword(userDTO.password) } returns hashedPassword
            coEvery { userRepository.findByUsername(userDTO.username) } returns user
            coEvery { userRepository.create(any()) } returns null
            coEvery { jwtService.createToken(any()) } returns "null"

            val result = authRepository.createAccount(userDTO)

            coVerify(exactly = 1) {
                passwordService.isPasswordValid(userDTO.password)
                passwordService.hashPassword(userDTO.password)
                userRepository.create(userDTO.copy(password = hashedPassword))
            }
            assertNull(result)
        }
}
