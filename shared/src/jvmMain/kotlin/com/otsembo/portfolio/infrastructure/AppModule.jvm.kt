package com.otsembo.portfolio.infrastructure

import com.otsembo.portfolio.business.LoginUC
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.koin.dsl.koinApplication
import org.koin.dsl.module

actual fun httpClient(): HttpClient = HttpClient(CIO)

fun jvmDIModule() =
    koinApplication {
        modules(appModule)
    }

fun desktopModule() =
    module {
        single<LoginUC> {
            LoginUC(client = get())
        }
    }
