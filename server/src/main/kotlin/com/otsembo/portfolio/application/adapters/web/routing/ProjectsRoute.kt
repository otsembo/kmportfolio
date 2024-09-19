package com.otsembo.portfolio.application.adapters.web.routing

import com.otsembo.portfolio.domain.mappers.AppState
import com.otsembo.portfolio.domain.models.ProjectDTO
import com.otsembo.portfolio.infrastructure.repository.IProjectsRepository
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.projectsRoute() {
    val projectsRepository: IProjectsRepository by inject()

    routing {
        route(RouteUtils.PROJECTS) {
            get("all") {
                val projects = projectsRepository.findAll()
                call.respond(AppState.Success(data = projects))
            }

            authenticate("auth-bearer") {
                post("new") {
                    val projectDTO = call.receive<ProjectDTO>()
                    val project = projectsRepository.create(projectDTO)
                    project?.let {
                        call.respond(AppState.Success(data = it))
                    } ?: call.respond(AppState.Error(message = "An error occurred"))
                }

                put("update/{id}") {
                    val id = call.parameters["id"]?.toLong()
                    val projectDTO = call.receive<ProjectDTO>()
                    val project = projectsRepository.update(id!!, projectDTO)
                    project?.let {
                        call.respond(AppState.Success(data = it))
                    } ?: call.respond(AppState.Error(message = "An error occurred"))
                }

                delete("delete/{id}") {
                    val id = call.parameters["id"]?.toLong()
                    val project = projectsRepository.delete(id!!)
                    if (project) {
                        call.respond(AppState.Success(data = "Project deleted successfully"))
                    } else {
                        call.respond(AppState.Error(message = "An error occurred when deleting the project"))
                    }
                }
            }
        }
    }
}
