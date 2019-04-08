import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "1.3.21"
val ktorVersion = "1.1.3"
val kscriptAnnotVersion = "1.4"
val picoCliVersion = "3.9.6"
val logbackVersion = "1.2.3"
val slf4jSimpleVersion = "1.7.26"

plugins {
    kotlin("jvm") version "1.3.21"
    idea
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

group = "id.jasoet.kscript"
version = "1.0.0"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("script-runtime"))
    implementation(kotlin("reflect"))
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-locations:$ktorVersion")
    implementation("io.ktor:ktor-gson:$ktorVersion")
    implementation("info.picocli:picocli:$picoCliVersion")
    implementation("com.github.holgerbrandl:kscript-annotations:$kscriptAnnotVersion")
    implementation("org.slf4j:slf4j-simple:$slf4jSimpleVersion")

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

tasks.test {
    useJUnitPlatform {}

    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events("passed", "failed", "skipped")
    }
}

tasks.withType<KotlinCompile> {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"

    kotlinOptions {
        jvmTarget = "1.8"
        apiVersion = "1.3"
        languageVersion = "1.3"
        allWarningsAsErrors = true
    }
}

tasks.wrapper {
    gradleVersion = "5.3.1"
}
