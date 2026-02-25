import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.2.21"
    id("org.jetbrains.kotlin.plugin.spring") version "2.2.21"
    id("org.springframework.boot") version "4.0.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.flywaydb.flyway") version "11.17.0"
}

group = "com.ntc"
version = "0.0.1"
description = "Flexible, accessible"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.flywaydb:flyway-core:11.17.0")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.hibernate.orm:hibernate-spatial:7.1.11.Final")
    implementation("org.locationtech.jts:jts-core:1.19.0")
    implementation("org.flywaydb:flyway-database-postgresql:11.17.0")
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    implementation("org.bouncycastle:bcprov-jdk18on:1.83")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    runtimeOnly("org.flywaydb:flyway-database-postgresql")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    runtimeOnly("org.postgresql:postgresql")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")


    implementation(project(":Domain"))
    implementation(project(":Security"))
    implementation(project(":Api"))
    implementation(project(":Data"))
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    mainClass.set("com.ntc.shopree.ShopreeApplication")
}

fun BootRun.loadEnvFile() {
    val envFile = file("${project.rootDir}/.env")
    if (envFile.exists()) {
        println("[DEV] Loading environment variables from ${envFile.absolutePath}")
        envFile.readLines()
            .filter { it.isNotBlank() && !it.startsWith("#")}
            .forEach { line ->
                val parts = line.split("=", limit = 2)
                if (parts.size == 2) {
                    val key = parts[0].trim()
                    val value = parts[1].trim()
                    environment(key, value)
                    println("[DEV] Loaded environment variable: $key=$value")
                }
            }
    }
    else {
        println("[DEV] No .env file found at ${envFile.absolutePath}.")
    }
}

tasks.named<BootRun>("bootRun") {
    args = listOf("--spring.profiles.active=dev")
    loadEnvFile()
    doFirst { println("[DEV] Starting application in development mode.") }
}

tasks.register<BootRun>("bootRunNoSec") {
    group = "application"
    description = "Runs the application with no security (No JWT required)"
    classpath = tasks.named<BootRun>("bootRun").get().classpath
    mainClass = tasks.named<BootRun>("bootRun").get().mainClass
    args = listOf("--spring.profiles.active=dev,no-security")
    loadEnvFile()
    doFirst { println("[DEV] Security: DISABLED - All endpoints are publicly accessible without authentication!") }
}

tasks.register("prepareKotlinBuildScriptModel"){}
