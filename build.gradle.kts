import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion

plugins {
    kotlin("jvm") version "2.0.21" apply false
    id("com.github.ivancarras.graphfity") version "1.2.0"
}

group = "com.akashvgnair"
version = "1.0-SNAPSHOT"

subprojects {

    // apply Kotlin plugin to all modules
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenCentral()
    }

    dependencies {
        // Existing
        add("testImplementation", kotlin("test"))

        // ✅ Added: JUnit 5 + MockK
        add("testImplementation", "org.junit.jupiter:junit-jupiter:5.10.2")
        add("testImplementation", "io.mockk:mockk:1.13.10")
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }

    // Java toolchain
    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    // Kotlin compilation target
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions.jvmTarget.set(
            org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
        )
    }
}

graphfityExtension {
    nodeTypesPath = "graphfityConfig/nodeTypes.json"
    projectRootName = ":main"
    graphImagePath = "build/reports/module-graph"
}