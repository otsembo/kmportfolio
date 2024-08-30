@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.otsembo.portfolio.application

import com.otsembo.portfolio.SERVER_PORT
import com.otsembo.portfolio.infrastructure.config.DBConfigs
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DBConfigs.initDB()
}
