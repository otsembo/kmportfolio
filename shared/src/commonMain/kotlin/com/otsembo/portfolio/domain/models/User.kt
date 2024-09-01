package com.otsembo.portfolio.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long = 0,
    val username: String,
    val token: String,
)

@Serializable
data class UserDTO(
    val email: String? = null,
    val username: String,
    val password: String,
)
