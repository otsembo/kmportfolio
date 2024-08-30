package com.otsembo.portfolio.domain.mappers

sealed class AppState<T> {
    data class Success<T>(
        val data: T,
    ) : AppState<T>()

    data class Error<T>(
        val message: String,
    ) : AppState<T>()

    data object Loading : AppState<Nothing>()
}
