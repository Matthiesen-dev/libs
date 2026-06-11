plugins {
    alias(libs.plugins.shadow) apply false
}

tasks.register<Copy>("copyJars") {
    group = "build"
    description = "Copies JAR files from project build directories to output directory"

    rootProject.childProjects.forEach { (string, project) ->
        logger.debug("Copying $string to ${project.name}")
        from("${project.projectDir}/build/libs/")
    }
    into("./output/")

    doFirst {
        delete(fileTree("./output/") {
            include("**/*")
        })
        file("./output/").mkdirs()
    }
}