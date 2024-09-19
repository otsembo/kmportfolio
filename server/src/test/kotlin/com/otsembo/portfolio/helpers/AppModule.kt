package com.otsembo.portfolio.helpers

import com.otsembo.portfolio.application.adapters.web.routing.RouteUtils.routesModule
import com.otsembo.portfolio.application.authModule
import com.otsembo.portfolio.application.serializationModule
import com.otsembo.portfolio.application.statusModule
import com.otsembo.portfolio.infrastructure.config.DBConfigs.dbModule
import io.ktor.server.application.Application

fun Application.testApp() {
    dbModule()
    serializationModule()
    statusModule()
    authModule()
    routesModule()
}
