package conventions

import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest

plugins {
    kotlin("multiplatform")
}

val libs = the<LibrariesForLibs>()

kotlin {
    js()
    sourceSets {
        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.assertk)
            }
        }
    }
}

tasks.withType<KotlinJsTest> {
    testLogging {
        // Make sure output from standard out or error is shown in Gradle output.
        showExceptions = true
        showCauses = true
        showStackTraces = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}
