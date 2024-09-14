package com.otsembo.portfolio.business

import com.otsembo.portfolio.domain.mappers.AppState
import com.otsembo.portfolio.domain.models.User
import com.otsembo.portfolio.domain.models.UserDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class LoginUC(
    private val client: HttpClient,
) {
    suspend operator fun invoke(
        password: String,
        username: String,
    ) = flow {
        emit(AppState.Loading)
        val response =
            client.post("auth/sign-in") {
                setBody(UserDTO(password = password, username = username))
            }

        val appResponse =
            try {
                response.body<AppState.Success<User?>>()
            } catch (e: Exception) {
                response.body<AppState.Error>()
            }

        emit(
            appResponse,
        )
    }.catch {
        emit(AppState.Error(message = it.message ?: "An error occurred"))
    }
}
