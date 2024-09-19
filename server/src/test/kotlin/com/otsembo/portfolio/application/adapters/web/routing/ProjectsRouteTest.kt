package com.otsembo.portfolio.application.adapters.web.routing

import com.otsembo.portfolio.domain.mappers.AppState
import com.otsembo.portfolio.domain.models.Project
import com.otsembo.portfolio.domain.models.ProjectDTO
import com.otsembo.portfolio.domain.models.User
import com.otsembo.portfolio.domain.models.UserDTO
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class ProjectsRouteTest {
    private lateinit var userToken: String

    @BeforeTest
    fun setUp() =
        testApplication {
            setUpEnv()
            val userDTO = UserDTO("testmail1", "testuser1", "passValid.1234")
            val response =
                client.post("${RouteUtils.AUTH}/register") {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(userDTO))
                }

            val responseBody = Json.decodeFromString<AppState.Success<User?>>(response.bodyAsText())

            userToken = responseBody.data?.token ?: "blankToken"
        }

    @Test
    fun testGetAllProjects() =
        testApplication {
            setUpEnv()
            val response = client.get("${RouteUtils.PROJECTS}/all")
            assertEquals(HttpStatusCode.OK, response.status)
            val responseBody = Json.decodeFromString<AppState.Success<List<Project>>>(response.bodyAsText())
            assertEquals(listOf<Project>(), responseBody.data)
        }

    @Test
    fun testCreateProjectSuccess() =
        testApplication {
            setUpEnv()
            val projectDTO = ProjectDTO(0L, "New Project", "New Description", "https://example.com")
            val response =
                client.post("${RouteUtils.PROJECTS}/new") {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(projectDTO))
                    bearerAuth(userToken)
                }
            assertEquals(HttpStatusCode.OK, response.status)
            val responseBody = Json.decodeFromString<AppState.Success<Project>>(response.bodyAsText())
            assertNotNull(responseBody.data)
        }

    private fun ApplicationTestBuilder.setUpEnv() {
        environment {
            config = ApplicationConfig("test-application.conf")
        }
    }
}
