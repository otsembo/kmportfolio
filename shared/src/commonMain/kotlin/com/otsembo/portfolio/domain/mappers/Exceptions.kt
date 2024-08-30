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
