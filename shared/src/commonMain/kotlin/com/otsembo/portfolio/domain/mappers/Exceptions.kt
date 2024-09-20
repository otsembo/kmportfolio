package com.otsembo.portfolio.domain.mappers

sealed class AppException(
    message: String,
) : Exception(message)

class AuthException(
    message: String,
) : AppException(message)

class ServerException(
    message: String,
) : AppException(message)

class ProjectException(
    message: String,
) : AppException(message)

inline fun <E : AppException> appException(
    value: Boolean,
    lazyMessage: () -> Any,
) {
    if (!value) {
        val message = lazyMessage()
        throw Exception(message.toString()) as E
    }
}
