plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "cabBooking"

include(":shared-kernel")
include(":main")
include(":rider-infrastructure", ":rider-application", ":rider-domain")
include(":driver-infrastructure", ":driver-application", ":driver-domain")
include(":booking-infrastructure", ":booking-application", ":booking-domain")
include(":fleet-infrastructure", ":fleet-application", ":fleet-domain")
include(":ops-infrastructure", ":ops-application", ":ops-domain")
include(":test-support")
