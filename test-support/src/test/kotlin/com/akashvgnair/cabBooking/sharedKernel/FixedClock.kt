package com.akashvgnair.cabBooking.sharedKernel

import com.akashvgnair.cabBooking.time.FixedClock
import org.junit.jupiter.api.Test
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


class FixedClockTest {
    @Test
    fun `should always return the fixed instant`() {
        val fixedTime = Instant.parse("2024-01-01T10:00:00Z")
        val clock = FixedClock(fixedTime).now()

        assertEquals(clock, fixedTime)
    }

    @Test
    fun `should always return same clock for same instants`() {
        val fixedTime = Instant.parse("2024-01-01T10:00:00Z")
        val clock1 = FixedClock(fixedTime).now()
        val clock2 = FixedClock(fixedTime).now()

        assertEquals(clock1, clock2)
    }

    @Test
    fun `should always return different clock for different instants`() {
        val fixedTime1 = Instant.parse("2024-01-01T10:00:00Z")
        val fixedTime2 = Instant.parse("2024-02-01T10:00:00Z")

        val clock1 = FixedClock(fixedTime1).now()
        val clock2 = FixedClock(fixedTime2).now()

        assertNotEquals(clock1, clock2)
    }
}

