package com.otsembo.portfolio.domain.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class JWTServiceTest {
    private val issuer = "testIssuer"
    private val audience = "testAudience"
    private val secret = "testSecret"
    private val jwtService = JWTService(issuer, audience, secret)

    @Test
    fun testCreateAndDecodeToken() {
        val username = "testUser"
        val token = jwtService.createToken(username)

        // Assert that the token is not empty
        assertTrue(token.isNotEmpty())

        // Decode the token and verify the username
        val decodedUsername = jwtService.decodeToken(token)
        assertEquals(username, decodedUsername)
    }

    @Test(expected = JWTVerificationException::class)
    fun testDecodeToken_InvalidToken() {
        // Attempt to decode an invalid token (e.g., an empty string)
        jwtService.decodeToken("")
    }

    @Test(expected = JWTVerificationException::class)
    fun testDecodeToken_ExpiredToken() {
        // Create a token that expires immediately
        val token =
            JWT
                .create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withExpiresAt(Date(System.currentTimeMillis() - 1000)) // Expired
                .sign(Algorithm.HMAC256(secret))

        // Attempt to decode the expired token
        jwtService.decodeToken(token)
    }
}
