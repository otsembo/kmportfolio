package com.otsembo.portfolio.infrastructure.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.transaction

suspend fun <F> dbQuery(block: () -> F?): F? {
    var result: F? = null
    withContext(Dispatchers.IO) {
        transaction {
            result = block()
        }
    }
    return result
}
