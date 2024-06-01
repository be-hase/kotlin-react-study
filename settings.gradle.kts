pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}

rootProject.name = "kotlin-react-study"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include("adding-interactivity")
include("describing-the-ui")
include("escape-hatches")
include("learn")
include("managing-state")
include("thinking-in-react")
include("tic-tac-toe")
include("tutorial")
