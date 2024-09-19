package com.otsembo.portfolio.domain.services

import com.otsembo.portfolio.domain.models.Project
import com.otsembo.portfolio.domain.models.ProjectDTO
import com.otsembo.portfolio.infrastructure.repository.IProjectsRepository

class ProjectService(
    private val repository: IProjectsRepository,
) {
    suspend fun createProject(projectDTO: ProjectDTO): Project? = repository.create(projectDTO)
}
