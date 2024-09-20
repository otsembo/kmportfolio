package com.otsembo.portfolio.application.adapters.web.routing

import com.otsembo.portfolio.domain.mappers.AppState
import com.otsembo.portfolio.domain.models.User
import com.otsembo.portfolio.domain.models.UserDTO
import com.otsembo.portfolio.infrastructure.repository.IAuthRepository
import com.otsembo.portfolio.infrastructure.repository.requireAuth
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.authRoute() {
    val authRepository: IAuthRepository by inject()
    routing {
        route(RouteUtils.AUTH) {
            post("sign-in") {
                val userDTO = call.receive<UserDTO>()
                val user = authRepository.signIn(info = userDTO)
                user?.let {
                    call.respond(AppState.Success<User?>(data = it))
                } ?: call.respond(
                    HttpStatusCode.NotFound,
                    AppState.Error(message = "User not found"),
                )
            }

            post("register") {
                val userDTO = call.receive<UserDTO>()
                val user = authRepository.createAccount(info = userDTO)
                user?.let {
                    call.respond(
                        HttpStatusCode.Created,
                        AppState.Success<User?>(data = it),
                    )
                } ?: call.respond(
                    HttpStatusCode.UnprocessableEntity,
                    AppState.Error(message = "An error occurred during registration. Try again later."),
                )
            }

            post("sign-out") {
                val token =
                    call.request.headers["Authorization"]
                        ?.split(" ")
                        ?.getOrNull(1)
                requireAuth(token != null) { "You have to be logged in to perform this action" }
                val hasLoggedOut = authRepository.signOut(token!!)
                if (hasLoggedOut) {
                    call.respond(AppState.Success(data = "Logged out successfully"))
                } else {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        AppState.Error(message = "An error occurred during logout. Try again later."),
                    )
                }
            }
        }
    }
}
