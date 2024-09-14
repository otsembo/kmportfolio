package com.otsembo.portfolio

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
