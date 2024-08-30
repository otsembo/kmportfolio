package com.otsembo.portfolio.application.adapters.web.routing

object RouteUtils {
    private const val BASE_URL = "/api/v1/"

    fun auth(endpoint: String) = "$BASE_URL/auth/$endpoint"
}
