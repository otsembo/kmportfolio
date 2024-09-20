package com.otsembo.portfolio.application.adapters.data.repository

import com.otsembo.portfolio.application.adapters.data.DBEntities
import com.otsembo.portfolio.domain.models.User
import com.otsembo.portfolio.domain.models.UserDTO
import com.otsembo.portfolio.infrastructure.db.dbQuery
import com.otsembo.portfolio.infrastructure.repository.IUserRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.selectAll

class UserRepository : IUserRepository {
    private val user = DBEntities.Users

    override suspend fun create(userDTO: UserDTO): User? =
        dbQuery {
            require(userDTO.email != null) { "You can not sign up without an email" }

            val users =
                user
                    .selectAll()
                    .where {
                        (user.username eq userDTO.username) or (user.email eq userDTO.email!!)
                    }.firstOrNull()

            // user with that email or username already exists
            if (users != null) return@dbQuery null

            val currTime = currentTime()

            val statement =
                user.insert {
                    it[username] = userDTO.username
                    it[email] = userDTO.email!!
                    it[password_hash] = userDTO.password
                    it[created_at] = currTime
                    it[updated_at] = currTime
                }

            User(
                id = statement[user.id].value,
                username = statement[user.username],
                token = "",
            )
        }

    override suspend fun find(id: Long): User? =
        dbQuery {
            val result =
                user
                    .select(user.id, user.username)
                    .where(user.id eq id)
                    .firstOrNull()
            result?.let {
                User(
                    id = it[user.id].value,
                    username = it[user.username],
                    token = "",
                )
            }
        }

    override suspend fun findByUsername(username: String): User? =
        dbQuery {
            val result =
                user
                    .select(user.id, user.username)
                    .where(user.username eq username)
                    .limit(1)
                    .firstOrNull()
            result?.let {
                User(
                    id = it[user.id].value,
                    username = it[user.username],
                    token = "",
                )
            }
        }

    override suspend fun findPasswordHash(username: String): String? =
        dbQuery {
            val result =
                user
                    .select(user.password_hash)
                    .where(user.username eq username)
                    .limit(1)
                    .firstOrNull()
            result?.let {
                it[user.password_hash]
            }
        }

    override suspend fun deleteUser(username: String): Boolean =
        dbQuery {
            user.deleteWhere { user.username eq username } > 0
        } ?: false
}
