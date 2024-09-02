package com.otsembo.portfolio.infrastructure.config

import com.otsembo.portfolio.application.adapters.data.DBEntities
import com.otsembo.portfolio.infrastructure.db.IDBConnection
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource

object DBConfigs {
    private val baseHikariConfig =
        HikariConfig().apply {
            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }

    // Database Engines - START

    private fun Application.engineDefinitions(engine: String): Map<String, String> =
        when (engine) {
            "h2" ->
                mapOf(
                    "jdbcDriver" to environment.config.property("db.h2.jdbcDriver").getString(),
                    "connURL" to environment.config.property("db.h2.connURL").getString(),
                )
            "postgresql" ->
                mapOf(
                    "jdbcDriver" to
                        environment.config.property("db.postgresql.jdbcDriver").getString(),
                    "connURL" to environment.config.property("db.postgresql.connURL").getString(),
                )
            else -> throw IllegalArgumentException("Invalid database engine: $engine")
        }

    private val Application.h2: DBConnection
        get() =
            DBConnection(
                dataSource =
                    HikariDataSource(
                        baseHikariConfig.apply {
                            jdbcUrl = engineDefinitions("h2")["connURL"]
                            driverClassName = engineDefinitions("h2")["jdbcDriver"]
                        },
                    ),
            )

    private val Application.postgresql: DBConnection
        get() =
            DBConnection(
                dataSource =
                    HikariDataSource(
                        baseHikariConfig.apply {
                            jdbcUrl = engineDefinitions("postgresql")["connURL"]
                            driverClassName = engineDefinitions("postgresql")["jdbcDriver"]
                        },
                    ),
            )

    // Database Engines - END

    private fun Application.getConnection(engine: String = "h2") =
        when (engine.lowercase()) {
            "h2" -> h2
            "postgresql" -> postgresql
            else -> throw IllegalArgumentException("Invalid database engine: $engine")
        }

    fun Application.dbModule(engine: String = "h2") {
        val connection = getConnection(engine)
        transaction {
            SchemaUtils.create(*DBEntities.schemas)
        }
        setUpMigrations(connection.dataSource)
    }

    private fun setUpMigrations(dataSource: DataSource) {
        Flyway
            .configure()
            .dataSource(dataSource)
            .load()
            .migrate()
    }

    class DBConnection(
        val dataSource: DataSource,
    ) : IDBConnection {
        override var db: Database? = null

        override fun setupDB(dataSource: DataSource): Database {
            dataSource.apply {
                this.loginTimeout = 2000
            }
            return Database.connect(dataSource)
        }

        override fun closeDB(): Boolean = false

        init {
            db = setupDB(dataSource)
        }
    }
}
