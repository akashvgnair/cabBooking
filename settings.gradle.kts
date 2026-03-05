plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "cabBooking"

include(":domain", ":application", ":infrastructure", ":shared-kernel")

project(":domain").projectDir = file("modules/domain")
project(":application").projectDir = file("modules/application")
project(":infrastructure").projectDir = file("modules/infrastructure")
project(":shared-kernel").projectDir = file("modules/shared-kernel")