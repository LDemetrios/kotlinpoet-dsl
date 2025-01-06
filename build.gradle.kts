import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.maven.publish)
}

val developerId: String by project
val developerName: String by project
val developerUrl: String by project
val releaseGroup: String by project
val releaseArtifact: String by project
val releaseVersion: String by project
val releaseDescription: String by project
val releaseUrl: String by project

val jdkVersion = JavaLanguageVersion.of(libs.versions.jdk.get())
val jreVersion = JavaLanguageVersion.of(libs.versions.jre.get())

kotlin {
    jvmToolchain(jdkVersion.asInt())
    explicitApi()
}

ktlint.version.set(libs.versions.ktlint.get())

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["kotlin"])

            groupId = "org.ldemetrios"   // Set your group ID
            artifactId = "kotlinpoet-dsl" // Set your artifact ID
            version = "0.1.0"         // Set your version
        }
    }
    repositories {
        mavenLocal()
    }
}

dependencies {
    ktlintRuleset(libs.rulebook.ktlint)

    api(libs.kotlinpoet)

    testImplementation(kotlin("test-junit", libs.versions.kotlin.get()))
    testImplementation(libs.truth)
}

tasks {
    compileJava {
        options.release = jreVersion.asInt()
    }
    compileKotlin {
        compilerOptions.jvmTarget
            .set(JvmTarget.fromTarget(JavaVersion.toVersion(jreVersion).toString()))
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }

    dokkaHtml {
        outputDirectory.set(layout.buildDirectory.dir("dokka/dokka/"))
    }
}

allprojects {
    group = releaseGroup
    version = releaseVersion
}
