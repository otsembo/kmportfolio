plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
}

group = "com.otsembo.portfolio"
version = "1.0.0"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.bundles.ktor)
    implementation(libs.bundles.exposed.db)
    testImplementation(libs.bundles.ktor.test)
    testImplementation(libs.kotlin.test.junit)
}
