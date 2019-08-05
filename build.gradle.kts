import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "1.3.40"
val ktorVersion = "1.2.2"
val kscriptAnnotVersion = "1.4"
val picoCliVersion = "3.9.6"
val logbackVersion = "1.2.3"
val slf4jSimpleVersion = "1.7.26"
val snakeYamlVersion = "1.24"
val kluentVersion = "1.49"
val jUnitVersion = "5.4.1"
val spekVersion = "2.0.1"
val easyRandomVersion = "4.0.0.RC1"

plugins {
    kotlin("jvm") version "1.3.21"
    jacoco
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
    implementation("org.yaml:snakeyaml:$snakeYamlVersion")
    implementation("id.jasoet:fun-gson:1.0.0")
    implementation("com.github.salomonbrys.kotson:kotson:2.5.0")
    implementation("id.jasoet:scripts-commons:1.0.3")
    implementation("org.slf4j:slf4j-simple:$slf4jSimpleVersion")
    implementation("io.ktor:ktor-client-apache:$ktorVersion")
    implementation("io.ktor:ktor-client-gson:$ktorVersion")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation("org.amshove.kluent:kluent:$kluentVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")
    testImplementation("org.amshove.kluent:kluent:$kluentVersion")
    testImplementation("org.jeasy:easy-random-core:$easyRandomVersion")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")
}

jacoco {
    toolVersion = "0.8.3"
}

tasks.jacocoTestReport {
    group = "Reporting"

    reports {
        xml.isEnabled = true
        html.isEnabled = true
        csv.isEnabled = false
    }
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
    extensions.configure(JacocoTaskExtension::class) {
        excludes = listOf("SimpleServerMock")
    }

    useJUnitPlatform {
        includeEngines("spec2", "junit-jupiter")
    }

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
