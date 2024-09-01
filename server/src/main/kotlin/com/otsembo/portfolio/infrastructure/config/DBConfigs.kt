package com.otsembo.portfolio.infrastructure.config

import com.otsembo.portfolio.application.adapters.data.DBEntities
import com.otsembo.portfolio.infrastructure.db.IDBConnection
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
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

    private val h2 =
        DBConnection(
            dataSource =
                HikariDataSource(
                    baseHikariConfig.apply {
                        jdbcUrl = "jdbc:h2:mem:test"
                        driverClassName = "org.h2.Driver"
                    },
                ),
        )

    // Database Engines - END

    private fun getConnection(engine: String = "h2") =
        when (engine.lowercase()) {
            "h2" -> h2
            else -> throw IllegalArgumentException("Invalid database engine: $engine")
        }

    fun initDB(engine: String = "h2") {
        getConnection(engine)
        transaction {
            SchemaUtils.create(*DBEntities.schemas)
        }
    }

    class DBConnection(
        dataSource: DataSource,
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
