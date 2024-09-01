package com.otsembo.portfolio.domain.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

class JWTService(
    private val issuer: String,
    private val audience: String,
    private val secret: String,
) {
    fun createToken(username: String): String =
        JWT
            .create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", username)
            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
            .sign(Algorithm.HMAC256(secret))

    fun decodeToken(token: String): String =
        JWT
            .require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()
            .verify(token)
            .getClaim("username")
            .asString()
}
