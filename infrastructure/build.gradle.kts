plugins {
    id("application")
}

application {
    mainClass.set("com.akashvgnair.cabbooking.MainKt")
}

dependencies {
    implementation(project(":shared-kernel"))
    implementation(project(":application"))
    implementation(project(":domain"))
}