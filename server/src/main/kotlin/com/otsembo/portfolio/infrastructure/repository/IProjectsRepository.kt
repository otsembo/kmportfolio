package com.otsembo.portfolio.infrastructure.repository

import com.otsembo.portfolio.domain.models.Project
import com.otsembo.portfolio.domain.models.ProjectDTO

interface IProjectsRepository : BaseRepository {
    suspend fun create(projectDTO: ProjectDTO): Project?

    suspend fun find(id: Long): Project?

    suspend fun findAll(): List<Project>

    suspend fun update(
        id: Long,
        projectDTO: ProjectDTO,
    ): Project?

    suspend fun delete(id: Long): Boolean
}
