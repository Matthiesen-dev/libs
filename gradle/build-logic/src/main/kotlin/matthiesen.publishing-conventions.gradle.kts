import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.api.Project
import org.gradle.api.credentials.PasswordCredentials
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.authentication.http.BasicAuthentication
import org.gradle.kotlin.dsl.configure

plugins {
    id("com.vanniktech.maven.publish")
}

// Seed an initial version when the project has not set one yet.
if (project.version.toString() == Project.DEFAULT_VERSION) {
    val moduleVersionKey = "version"
    val moduleVersionEnvKey = moduleVersionKey.uppercase()
    val resolvedVersion = providers.gradleProperty(moduleVersionKey)
        .orElse(providers.environmentVariable(moduleVersionEnvKey))
        .orElse(providers.environmentVariable("PROJECT_VERSION"))

    resolvedVersion.orNull?.let { project.version = it }
}

afterEvaluate {
    val publishVersion = project.version.toString()

    configure<PublishingExtension> {
        repositories {
            maven {
                name = "devMatthiesenMaven"
                url = uri(if (publishVersion.endsWith("SNAPSHOT"))
                    "https://maven.matthiesen.dev/snapshots"
                else "https://maven.matthiesen.dev/releases")
                credentials(PasswordCredentials::class)
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }
    }

    configure<MavenPublishBaseExtension> {
        coordinates(
            project.group.toString(),
            project.name,
            publishVersion
        )

        pom {
            name.set(project.name)
            description.set(project.property("description").toString())
            inceptionYear.set("2026")
            url.set(project.property("github_url").toString())
            licenses {
                license {
                    name.set(project.property("license").toString())
                    url.set(project.property("license_url").toString())
                    distribution.set(project.property("license_url").toString())
                }
            }
            developers {
                developer {
                    id.set(project.property("author_id").toString())
                    name.set(project.property("author").toString())
                    url.set(project.property("author_url").toString())
                }
            }
            scm {
                url.set(project.property("github_url").toString())
                connection.set("scm:git:git://${project.property("git_url").toString()}")
                developerConnection.set("scm:git:ssh://git@${project.property("git_url").toString()}")
            }
        }
    }

    pluginManager.withPlugin("com.gradleup.shadow") {
        val groupId = project.group.toString()
        val artifactId = project.name
        val version = project.version.toString()

        configure<PublishingExtension> {
            publications.create("shadow", MavenPublication::class.java) {
                from(components.getByName("shadow"))
                artifact(tasks.named("sourcesJar"))
                artifact(tasks.named("plainJavadocJar"))

                this.groupId = groupId
                this.artifactId = artifactId
                this.version = version

                pom {
                    name.set(project.name)
                    description.set(project.property("description").toString())
                    inceptionYear.set("2026")
                    url.set(project.property("github_url").toString())
                    licenses {
                        license {
                            name.set(project.property("license").toString())
                            url.set(project.property("license_url").toString())
                            distribution.set(project.property("license_url").toString())
                        }
                    }
                    developers {
                        developer {
                            id.set(project.property("author_id").toString())
                            name.set(project.property("author").toString())
                            url.set(project.property("author_url").toString())
                        }
                    }
                    scm {
                        url.set(project.property("github_url").toString())
                        connection.set("scm:git:git://${project.property("git_url").toString()}")
                        developerConnection.set("scm:git:ssh://git@${project.property("git_url").toString()}")
                    }
                }
            }
        }

        tasks.configureEach {
            if (name.contains("MavenPublication") && !name.contains("ShadowPublication")) {
                enabled = false
            }
        }
    }
}

