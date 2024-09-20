package com.otsembo.portfolio.application.adapters.web.routing

import com.otsembo.portfolio.domain.mappers.AppState
import com.otsembo.portfolio.domain.models.User
import com.otsembo.portfolio.domain.models.UserDTO
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.testApplication
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AuthRouteTest {
    private val passValid = "Password.123"

    @Test
    fun testSignInSuccess() =
        testApplication {
            environment {
                config = ApplicationConfig("test-application.conf")
            }
            val userDTO = UserDTO("testmail", "testuser", passValid)

            // create account
            client.post("${RouteUtils.AUTH}/register") {
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString(userDTO))
            }

            val response =
                client.post("${RouteUtils.AUTH}/sign-in") {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(value = userDTO))
                }

            assertEquals(HttpStatusCode.OK, response.status)
            val responseBody = Json.decodeFromString<AppState.Success<User?>>(response.bodyAsText())
            assertNotNull(responseBody.data)
        }

    @Test
    fun testSignInNotFound() =
        testApplication {
            environment {
                config = ApplicationConfig("test-application.conf")
            }
            val userDTO = UserDTO("testmail", "nonexistent", "password")

            val response =
                client.post("${RouteUtils.AUTH}/sign-in") {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(userDTO))
                }

            assertEquals(HttpStatusCode.Unauthorized, response.status)
            val responseBody = Json.decodeFromString<AppState.Error>(response.bodyAsText())
            assertEquals("User does not exist", responseBody.message)
        }

    @Test
    fun testRegisterSuccess() =
        testApplication {
            environment {
                config = ApplicationConfig("test-application.conf")
            }
            val userDTO = UserDTO("testmail1", "testuser1", passValid)

            val response =
                client.post("${RouteUtils.AUTH}/register") {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(userDTO))
                }

            assertEquals(HttpStatusCode.Created, response.status)
            val responseBody = Json.decodeFromString<AppState.Success<User?>>(response.bodyAsText())
            assertNotNull(responseBody.data)
        }

    @Test
    fun testRegisterFailure() =
        testApplication {
            environment {
                config = ApplicationConfig("test-application.conf")
            }
            val userDTO = UserDTO("testmail", "use", passValid)

            var response =
                client.post("${RouteUtils.AUTH}/register") {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(userDTO))
                }

            assertEquals(HttpStatusCode.Unauthorized, response.status)
            var responseBody = Json.decodeFromString<AppState.Error>(response.bodyAsText())
            assertEquals(
                "Username cannot be less than 4 characters",
                responseBody.message,
            )

            response =
                client.post("${RouteUtils.AUTH}/register") {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(userDTO.copy(username = "newuser", password = "sds")))
                }
            assertEquals(HttpStatusCode.Unauthorized, response.status)
            responseBody = Json.decodeFromString<AppState.Error>(response.bodyAsText())
            assertEquals(
                "Password is not valid. It must be at least 8 characters long and contain" +
                    " at least one uppercase letter, one lowercase letter and one digit.",
                responseBody.message,
            )
        }

    @Test
    fun testSignOutSuccess() =
        testApplication {
            environment {
                config = ApplicationConfig("test-application.conf")
            }

            val response =
                client.post("${RouteUtils.AUTH}/sign-out") {
                    bearerAuth("sampleToken")
                }

            assertEquals(HttpStatusCode.OK, response.status)
            val responseBody = Json.decodeFromString<AppState.Success<String>>(response.bodyAsText())
            assertEquals("Logged out successfully", responseBody.data)
        }
}
