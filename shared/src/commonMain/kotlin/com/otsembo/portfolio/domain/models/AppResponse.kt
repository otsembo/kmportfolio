package com.otsembo.portfolio.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class AppResponse<T>(
    val message: String,
    val data: T? = null,
)
