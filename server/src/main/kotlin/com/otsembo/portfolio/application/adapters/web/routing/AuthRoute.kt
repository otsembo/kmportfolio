package com.otsembo.portfolio.application.adapters.web.routing

import com.otsembo.portfolio.domain.models.UserDTO
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.authRoute() {
    routing {
        post(RouteUtils.auth("/sign-in")) {
            val user = call.receive<UserDTO>()
        }
    }
}
