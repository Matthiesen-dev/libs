plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.gradle)
    implementation(libs.ideaExt)
    implementation(libs.publish)
    implementation(libs.shadow)
}

