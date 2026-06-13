import org.gradle.api.JavaVersion
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

group = property("maven_group").toString()
version = providers.environmentVariable("VERSION")
    .orElse(project.property("version").toString()).get()

repositories {
    mavenCentral  {
        content {
            excludeGroup("dev.matthiesen")
        }
    }
    maven("https://repo.faststats.dev/releases")
    maven("https://maven.matthiesen.dev/releases") {
        name = "devMatthiesenMavenReleases"
    }
    maven("https://maven.matthiesen.dev/snapshots") {
        name = "devMatthiesenMavenSnapshots"
    }
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(21)
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}


