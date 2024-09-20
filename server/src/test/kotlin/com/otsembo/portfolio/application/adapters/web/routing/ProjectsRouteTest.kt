package com.otsembo.portfolio.application.adapters.web.routing

import com.otsembo.portfolio.application.adapters.data.repository.UserRepository
import com.otsembo.portfolio.domain.mappers.AppState
import com.otsembo.portfolio.domain.models.Project
import com.otsembo.portfolio.domain.models.ProjectDTO
import com.otsembo.portfolio.domain.models.User
import com.otsembo.portfolio.domain.models.UserDTO
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
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
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ProjectsRouteTest {
    private lateinit var userToken: String
    private val projectDTO = ProjectDTO(0L, "New Project", "New Description", "https://example.com")
    private val userDTO = UserDTO("testmail1", "testuser1", "passValid.1234")

    @BeforeTest
    fun setUp() =
        testApplication {
            setUpEnv()
            val response =
                client.post("${RouteUtils.AUTH}/register") {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(userDTO))
                }
            val responseBody = Json.decodeFromString<AppState.Success<User?>>(response.bodyAsText())
            userToken = responseBody.data?.token ?: "blankToken"
        }

    @AfterTest
    fun tearDown() =
        testApplication {
            setUpEnv()
            UserRepository().deleteUser(userDTO.username)
        }

    @Test
    fun testGetAllProjects() =
        testApplication {
            setUpEnv()
            addTestProject()
            val response = client.get("${RouteUtils.PROJECTS}/all")
            assertEquals(HttpStatusCode.OK, response.status)
            val responseBody = Json.decodeFromString<AppState.Success<List<Project>>>(response.bodyAsText())
            assertTrue(responseBody.data.isNotEmpty())
        }

    @Test
    fun testCreateProjectSuccess() =
        testApplication {
            setUpEnv()
            val response = addTestProject()
            assertEquals(HttpStatusCode.Created, response.status)
            val responseBody = Json.decodeFromString<AppState.Success<Project>>(response.bodyAsText())
            assertNotNull(responseBody.data)
        }

    @Test
    fun testCreateProjectFailure() =
        testApplication {
            setUpEnv()
            val response = addTestProject(pDTO = projectDTO.copy(title = ""))
            assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseBody = Json.decodeFromString<AppState.Error>(response.bodyAsText())
            assertEquals("Project title cannot be blank", responseBody.message)
        }

    @Test
    fun testUpdateProjectSuccess() =
        testApplication {
            setUpEnv()
            val projectResponse = addTestProject()
            val project = Json.decodeFromString<AppState.Success<Project>>(projectResponse.bodyAsText())
            val response =
                client.put("${RouteUtils.PROJECTS}/update/${project.data.id}") {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(projectDTO.copy(title = "Updated Project")))
                    bearerAuth(userToken)
                }
            assertEquals(HttpStatusCode.OK, response.status)
            val responseBody = Json.decodeFromString<AppState.Success<Project>>(response.bodyAsText())
            assertEquals("Updated Project", responseBody.data.title)
        }

    @Test
    fun testUpdateProjectFailure() =
        testApplication {
            setUpEnv()
            val projectResponse = addTestProject()
            val project = Json.decodeFromString<AppState.Success<Project>>(projectResponse.bodyAsText())
            val id = project.data.id
            val response =
                client.put("${RouteUtils.PROJECTS}/update/$id") {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(projectDTO.copy(title = "")))
                    bearerAuth(userToken)
                }
            assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseBody = Json.decodeFromString<AppState.Error>(response.bodyAsText())
            assertEquals("Project title cannot be blank", responseBody.message)
        }

    @Test
    fun testDeleteProjectSuccess() =
        testApplication {
            setUpEnv()
            val projectResponse = addTestProject()
            val project = Json.decodeFromString<AppState.Success<Project>>(projectResponse.bodyAsText())
            val id = project.data.id
            val response =
                client.delete("${RouteUtils.PROJECTS}/delete/$id") {
                    bearerAuth(userToken)
                }

            assertEquals(HttpStatusCode.OK, response.status)
            val responseBody = Json.decodeFromString<AppState.Success<String>>(response.bodyAsText())
            assertEquals("Project deleted successfully", responseBody.data)
        }

    @Test
    fun testDeleteProjectFailure() =
        testApplication {
            setUpEnv()
            val id = Long.MAX_VALUE
            val response =
                client.delete("${RouteUtils.PROJECTS}/delete/$id") {
                    bearerAuth(userToken)
                }
            assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseBody = Json.decodeFromString<AppState.Error>(response.bodyAsText())
            assertEquals("An error occurred when deleting the project", responseBody.message)
        }

    private fun ApplicationTestBuilder.setUpEnv() {
        environment {
            config = ApplicationConfig("test-application.conf")
        }
    }

    private suspend fun ApplicationTestBuilder.addTestProject(pDTO: ProjectDTO = projectDTO) =
        client.post("${RouteUtils.PROJECTS}/new") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(pDTO))
            bearerAuth(userToken)
        }
}
