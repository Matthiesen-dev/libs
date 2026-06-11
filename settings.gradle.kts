rootProject.name = "libs"

pluginManagement {
    repositories {
        gradlePluginPortal()
    }

    includeBuild("gradle/build-logic")
}

include("faststats")