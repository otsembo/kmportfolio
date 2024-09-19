package com.otsembo.portfolio.application.adapters.data.repository

import com.otsembo.portfolio.application.adapters.data.DBEntities
import com.otsembo.portfolio.domain.models.Project
import com.otsembo.portfolio.domain.models.ProjectDTO
import com.otsembo.portfolio.infrastructure.db.dbQuery
import com.otsembo.portfolio.infrastructure.repository.IProjectsRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class ProjectsRepository : IProjectsRepository {
    private val projects = DBEntities.Projects

    override suspend fun create(projectDTO: ProjectDTO): Project? =
        dbQuery {
            require(projectDTO.title.isNotBlank()) { "Project title cannot be blank" }
            require(projectDTO.description.isNotBlank()) { "Project description cannot be blank" }
            require(projectDTO.url.isNotBlank()) { "Project url cannot be blank" }

            val currTime = currentTime()

            val statement =
                projects.insert {
                    it[title] = projectDTO.title
                    it[description] = projectDTO.description
                    it[url] = projectDTO.url
                    it[created_at] = currTime
                    it[updated_at] = currTime
                }

            Project(
                id = statement[projects.id].value,
                title = statement[projects.title],
                description = statement[projects.description],
                url = statement[projects.url],
                created_at = statement[projects.created_at].toString(),
                updated_at = statement[projects.updated_at].toString(),
            )
        }

    override suspend fun find(id: Long): Project? =
        dbQuery {
            val result = projects.selectAll().where { projects.id eq id }.firstOrNull()
            if (result == null) return@dbQuery null
            Project(
                id = result[projects.id].value,
                title = result[projects.title],
                description = result[projects.description],
                url = result[projects.url],
                created_at = result[projects.created_at].toString(),
                updated_at = result[projects.updated_at].toString(),
            )
        }

    override suspend fun findAll(): List<Project> =
        dbQuery {
            projects.selectAll().mapNotNull {
                Project(
                    id = it[projects.id].value,
                    title = it[projects.title],
                    description = it[projects.description],
                    url = it[projects.url],
                    created_at = it[projects.created_at].toString(),
                    updated_at = it[projects.updated_at].toString(),
                )
            }
        } ?: emptyList()

    override suspend fun update(
        id: Long,
        projectDTO: ProjectDTO,
    ): Project? =
        dbQuery {
            val project = find(id) ?: return@dbQuery null
            require(projectDTO.title.isNotBlank()) { "Project title cannot be blank" }
            require(projectDTO.description.isNotBlank()) { "Project description cannot be blank" }
            require(projectDTO.url.isNotBlank()) { "Project url cannot be blank" }
            projects.update({ projects.id eq id }) {
                it[title] = projectDTO.title
                it[description] = projectDTO.description
                it[url] = projectDTO.url
                it[updated_at] = currentTime()
            }
            project.copy(
                title = projectDTO.title,
                description = projectDTO.description,
                url = projectDTO.url,
                updated_at = currentTime().toString(),
            )
        }

    override suspend fun delete(id: Long): Boolean =
        dbQuery {
            projects.deleteWhere { projects.id eq id } > 0
        } ?: false
}
