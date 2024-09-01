package com.otsembo.portfolio.domain.services

import at.favre.lib.crypto.bcrypt.BCrypt

class PasswordService {
    fun hashPassword(password: String): String {
        val bcrypt = BCrypt.withDefaults()
        return bcrypt.hashToString(12, password.toCharArray())
    }

    fun verifyPassword(
        password: String,
        hashedPassword: String,
    ): Boolean {
        val bcrypt = BCrypt.verifyer()
        return bcrypt.verify(password.toCharArray(), hashedPassword).verified
    }

    fun isPasswordValid(password: String): Boolean =
        password.length >= 8 &&
            password.any { it.isUpperCase() } &&
            password.any { it.isLowerCase() } &&
            password.any { it.isDigit() }
}
