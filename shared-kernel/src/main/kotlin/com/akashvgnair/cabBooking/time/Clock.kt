package com.akashvgnair.cabBooking.time

import java.time.Instant

interface Clock {
    fun now(): Instant
}