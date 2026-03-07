package com.akashvgnair.cabBooking.time

import java.time.Instant

class FixedClock(private val fixedTime: Instant): Clock {
    override fun now(): Instant = fixedTime
}