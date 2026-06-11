import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("matthiesen.project-conventions")
}

pluginManager.withPlugin("com.gradleup.shadow") {
    tasks.named<ShadowJar>("shadowJar") {
        archiveClassifier.set("")
        archiveBaseName.set(project.name)
    }
}

