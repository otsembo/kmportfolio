package com.otsembo.portfolio.infrastructure.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync

suspend fun <F> dbQuery(block: suspend () -> F?): F? =
    withContext(Dispatchers.IO) {
        suspendedTransactionAsync {
            block()
        }.await()
    }
