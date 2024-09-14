package com.otsembo.portfolio.infrastructure

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

expect fun httpClient(): HttpClient

val appModule =
    module {
        single<HttpClient> {
            httpClient().config {
                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                        },
                    )
                }
                defaultRequest {
                    url {
                        protocol = URLProtocol.HTTP
                        host = "127.0.0.1"
                        port = 8080
                        path("api/v1/")
                    }
                    setAttributes {
                        contentType(ContentType.Application.Json)
                    }
                }
            }
        }
    }
