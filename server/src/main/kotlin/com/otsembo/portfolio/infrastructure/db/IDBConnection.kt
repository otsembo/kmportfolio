package com.otsembo.portfolio.infrastructure.db

import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

/**
 * An interface responsible for managing a database connection.
 *
 * It establishes a connection to the database using the provided [DataSource]* and provides methods for setting up and closing the connection.
 *
 * @property db The database connection object.
 */
interface IDBConnection {
    var db: Database?

    /**
     * Sets up the database connection using the provided [DataSource].
     *
     * @param dataSource The data source to connect to.
     * @return The established database connection.
     */
    fun setupDB(dataSource: DataSource): Database

    /**
     * Closes the database connection.
     *
     * @return `true` if the connection was successfully closed, `false` otherwise.
     */
    fun closeDB(): Boolean
}
