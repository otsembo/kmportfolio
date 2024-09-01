package com.otsembo.portfolio.application.adapters.web.routing

import io.ktor.server.application.Application

object RouteUtils {
    private const val BASE_URL = "/api/v1"

    fun auth(endpoint: String) = "$BASE_URL/auth/$endpoint"

    fun Application.routesModule() {
        authRoute()
    }
}
