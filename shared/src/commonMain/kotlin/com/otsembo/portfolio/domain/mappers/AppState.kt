package com.otsembo.portfolio.domain.mappers

import kotlinx.serialization.Serializable

sealed class AppState {
    @Serializable
    data class Success<T>(
        val data: T,
    ) : AppState()

    @Serializable
    data class Error(
        val message: String,
    ) : AppState()

    data object Loading : AppState()
}
