package com.otsembo.portfolio.di

import com.otsembo.portfolio.application.adapters.data.repository.AuthRepository
import com.otsembo.portfolio.application.adapters.data.repository.UserRepository
import com.otsembo.portfolio.domain.services.JWTService
import com.otsembo.portfolio.domain.services.PasswordService
import com.otsembo.portfolio.infrastructure.repository.IUserRepository
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.diModule() {
    install(Koin) {
        slf4jLogger(level = Level.ERROR)
        modules(appModule())
    }
}

fun Application.appModule() =
    module {
        single<JWTService> {
            val issuer =
                environment.config.propertyOrNull("jwt.issuer")?.getString()
                    ?: "http://0.0.0.0:8080/"
            val audience =
                environment.config.propertyOrNull("jwt.audience")?.getString()
                    ?: "http://0.0.0.0:8080/hello"
            val secret =
                environment.config.propertyOrNull("jwt.secret")?.getString()
                    ?: "secret"

            JWTService(issuer, audience, secret)
        }

        single<PasswordService> {
            PasswordService()
        }

        single<IUserRepository> {
            UserRepository()
        }

        single<AuthRepository> {
            AuthRepository(
                jwtService = get(),
                passwordService = get(),
                userRepository = get(),
            )
        }
    }
