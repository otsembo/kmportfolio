package com.otsembo.portfolio.application.adapters.data

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object DBEntities {
    val schemas =
        arrayOf(
            Users,
            Projects,
            Blogs,
            Comments,
        )

    object Users : LongIdTable("users") {
        val username = varchar("username", 255).uniqueIndex()
        val email = varchar("email", 255).uniqueIndex()
        val password_hash = varchar("password_hash", 255)
        val created_at = datetime("created_at")
        val updated_at = datetime("updated_at")
    }

    object Projects : LongIdTable("projects") {
        val title = varchar("title", 255)
        val description = text("description")
        val url = varchar("url", 255)
        val created_at = datetime("created_at")
        val updated_at = datetime("updated_at")
    }

    object Blogs : LongIdTable("blogs") {
        val title = varchar("title", 255)
        val content = text("content")
        val created_at = datetime("created_at")
        val updated_at = datetime("updated_at")
    }

    object Comments : LongIdTable("comments") {
        val blog_id = reference("blog_id", Blogs)
        val author = varchar("author", 255)
        val content = text("content")
        val created_at = datetime("created_at")
        val updated_at = datetime("updated_at")
    }
}
