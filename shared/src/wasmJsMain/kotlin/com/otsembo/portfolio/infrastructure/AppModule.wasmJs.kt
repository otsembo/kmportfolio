package com.otsembo.portfolio.infrastructure

import io.ktor.client.HttpClient

actual fun httpClient(): HttpClient = HttpClient()
