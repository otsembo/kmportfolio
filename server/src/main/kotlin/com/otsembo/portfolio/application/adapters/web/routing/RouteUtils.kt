package com.otsembo.portfolio.application.adapters.web.routing

import io.ktor.server.application.Application
import io.ktor.server.routing.routing

object RouteUtils {
    private const val BASE_URL = "/api/v1"

    const val AUTH = "$BASE_URL/auth"

    const val PROJECTS = "$BASE_URL/projects"

    fun Application.routesModule() {
        routing {
            authRoute()
            projectsRoute()
        }
    }
}
