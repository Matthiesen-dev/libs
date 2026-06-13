plugins {
    id("com.gradleup.shadow")
    id("matthiesen.shadow-platform-conventions")
    id("matthiesen.publishing-conventions")
}

val shadowBundle: Configuration by configurations.creating

dependencies {
    implementation("dev.faststats.metrics:config:${version}")
    shadowBundle("dev.faststats.metrics:config:${version}")
    implementation("dev.faststats.metrics:core:${version}")
    shadowBundle("dev.faststats.metrics:core:${version}")
}

tasks.shadowJar {
    configurations.set(listOf(shadowBundle))
    relocate("dev.faststats", "dev.matthiesen.libs.faststats")
}