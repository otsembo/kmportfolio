@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.otsembo.portfolio.application

import com.otsembo.portfolio.application.adapters.web.routing.RouteUtils.routesModule
import com.otsembo.portfolio.di.diModule
import com.otsembo.portfolio.domain.mappers.AppState
import com.otsembo.portfolio.domain.mappers.AuthException
import com.otsembo.portfolio.infrastructure.config.DBConfigs.dbModule
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.netty.EngineMain
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import kotlinx.serialization.json.Json

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    val dbEngine =
        environment
            .config
            .propertyOrNull("db.engine")
            ?.getString()

    dbModule(
        engine = dbEngine?.lowercase() ?: "postgresql",
    )
    diModule()
    serializationModule()
    statusModule()
    routesModule()
}

fun Application.serializationModule() {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            },
        )
    }
}

fun Application.statusModule() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when (cause) {
                is AuthException ->
                    call.respond(
                        status = HttpStatusCode.Unauthorized,
                        AppState.Error(message = cause.message ?: "An error occurred"),
                    )
                else ->
                    call.respond(
                        status = HttpStatusCode.InternalServerError,
                        AppState.Error(message = cause.message ?: "An error occurred"),
                    )
            }
        }
    }
}
