package com.otsembo.portfolio.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Project(
    val id: Long,
    val title: String,
    val description: String,
    val url: String,
    val created_at: String,
    val updated_at: String,
)

@Serializable
data class ProjectDTO(
    val id: Long = 0L,
    val title: String,
    val description: String,
    val url: String,
)
