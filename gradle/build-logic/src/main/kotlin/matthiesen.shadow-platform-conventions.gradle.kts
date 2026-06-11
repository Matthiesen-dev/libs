import com.github.jengelman.gradle.plugins.shadow.ShadowExtension
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.configure

plugins {
    id("matthiesen.project-conventions")
}

pluginManager.withPlugin("com.gradleup.shadow") {
    extensions.configure<ShadowExtension>("shadow") {
        addShadowVariantIntoJavaComponent.set(false)
    }

    tasks.named<ShadowJar>("shadowJar") {
        archiveClassifier.set("")
        archiveBaseName.set(project.name)
    }

    tasks.named<Jar>("jar") {
        archiveClassifier.set("thin")
    }
}

