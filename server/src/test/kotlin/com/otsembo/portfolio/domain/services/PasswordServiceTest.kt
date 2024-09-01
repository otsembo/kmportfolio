package com.otsembo.portfolio.domain.services

import kotlin.test.Test
import kotlin.test.assertNotEquals

class PasswordServiceTest {
    private val passwordService = PasswordService()

    @Test
    fun testHashPassword() {
        val password = "password123"
        val hashedPassword = passwordService.hashPassword(password)
        assert(hashedPassword.isNotEmpty()) {
            "Password hashing failed"
        }
        assertNotEquals(password, hashedPassword, "Password hashing failed")
    }

    @Test
    fun testVerifyPassword() {
        val password = "password123"
        val hashedPassword = passwordService.hashPassword(password)
        assert(passwordService.verifyPassword(password, hashedPassword)) {
            "Password verification failed"
        }
    }
}
