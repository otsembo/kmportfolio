package com.otsembo.portfolio.di

import com.otsembo.portfolio.application.adapters.data.repository.AuthRepository
import com.otsembo.portfolio.application.adapters.data.repository.ProjectsRepository
import com.otsembo.portfolio.application.adapters.data.repository.UserRepository
import com.otsembo.portfolio.domain.services.JWTService
import com.otsembo.portfolio.domain.services.PasswordService
import com.otsembo.portfolio.infrastructure.repository.IAuthRepository
import com.otsembo.portfolio.infrastructure.repository.IProjectsRepository
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

fun appModule() =
    module {
        single<JWTService> {
            val issuer = System.getenv("JWT_ISSUER") ?: "issuer"
            val audience = System.getenv("JWT_AUDIENCE") ?: "audience"
            val secret = System.getenv("JWT_SECRET") ?: "dummy_invalid_secret"
            JWTService(issuer, audience, secret)
        }

        single<PasswordService> {
            PasswordService()
        }

        single<IUserRepository> {
            UserRepository()
        }

        single<IAuthRepository> {
            AuthRepository(
                jwtService = get(),
                passwordService = get(),
                userRepository = get(),
            )
        }

        single<IProjectsRepository> {
            ProjectsRepository()
        }
    }
