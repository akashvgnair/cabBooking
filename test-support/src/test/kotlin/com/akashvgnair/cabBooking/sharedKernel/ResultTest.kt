package com.akashvgnair.cabBooking.sharedKernel

import com.akashvgnair.cabBooking.result.Result
import kotlin.test.assertEquals
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ResultTest {

    // ── Construction ────────────────────────────────────────────

    @Test
    fun `should create Success with value`() {
        val result = Result.Success(42)
        assertEquals(42, result.value)
    }

    @Test
    fun `should create Failure with error`() {
        val result = Result.Failure("something went wrong")
        assertEquals("something went wrong", result.error)
    }

    // ── isSuccess / isFailure ────────────────────────────────────

    @Test
    fun `should return true for isSuccess when Success`() {
        val result = Result.Success(42)
        assertTrue(result.isSuccess)
    }

    @Test
    fun `should return false for isSuccess when Failure`() {
        val result = Result.Failure("error")
        assertFalse(result.isSuccess)
    }

    @Test
    fun `should return true for isFailure when Failure`() {
        val result = Result.Failure("error")
        assertTrue(result.isFailure)
    }

    @Test
    fun `should return false for isFailure when Success`() {
        val result = Result.Success(42)
        assertFalse(result.isFailure)
    }

    // ── map ─────────────────────────────────────────────────────

    @Test
    fun `should transform value when Success`() {
        val result = Result.Success(5)
        val mapped = result.map { it * 2 }
        assertEquals(Result.Success(10), mapped)
    }

    @Test
    fun `should not transform when Failure`() {
        val result: Result<Int, String> = Result.Failure("error")
        val mapped = result.map { it * 2 }
        assertEquals(Result.Failure("error"), mapped)
    }

    @Test
    fun `should propagate Failure without executing map block`() {
        var blockExecuted = false
        val result: Result<Int, String> = Result.Failure("error")

        result.map {
            blockExecuted = true
            it * 2
        }

        assertFalse(blockExecuted)
    }

    // ── flatMap ──────────────────────────────────────────────────

    @Test
    fun `should chain to next Success when Success`() {
        val result = Result.Success(5)
        val chained = result.flatMap { Result.Success(it * 2) }
        assertEquals(Result.Success(10), chained)
    }

    @Test
    fun `should chain to Failure when first is Failure`() {
        val result: Result<Int, String> = Result.Failure("error")
        val chained = result.flatMap { Result.Success(it * 2) }
        assertEquals(Result.Failure("error"), chained)
    }

    @Test
    fun `should not execute flatMap block when Failure`() {
        var blockExecuted = false
        val result: Result<Int, String> = Result.Failure("error")

        result.flatMap {
            blockExecuted = true
            Result.Success(it * 2)
        }

        assertFalse(blockExecuted)
    }


    // ── fold ─────────────────────────────────────────────────────

    @Test
    fun `should execute success block when Success`() {
        val result = Result.Success(5)
        val outcome = result.fold(
            onSuccess = { "got $it" },
            onFailure = { "failed" }
        )

        assertEquals("got 5", outcome)
    }

    @Test
    fun `should execute failure block when Failure`() {
        val result: Result<Int, String> = Result.Failure("error")
        val outcome = result.fold(
            onSuccess = { "got $it" },
            onFailure = { "failed: $it" }
        )

        assertEquals("failed: error", outcome)
    }

    @Test
    fun `should not execute failure block when Success`() {
        var failureBlockExecuted = false
        val result = Result.Success(5)

        result.fold(
            onSuccess = { it },
            onFailure = {
                failureBlockExecuted = true
                it
            }
        )

        assertFalse(failureBlockExecuted)
    }

    @Test
    fun `should not execute success block when Failure`() {
        var successBlockExecuted = false
        val result: Result<Int, String> = Result.Failure("error")

        result.fold(
            onSuccess = {
                successBlockExecuted = true
                it
            },
            onFailure = { 0 }
        )

        assertFalse(successBlockExecuted)
    }

    // ── getOrElse ────────────────────────────────────────────────

    @Test
    fun `should return correct value when Success`() {
        val result = Result.Success(42)
        assertEquals(42, result.getOrElse(0))
    }

    @Test
    fun `should return default when Failure`() {
        val result: Result<Int, String> = Result.Failure("error")
        assertEquals(0, result.getOrElse(0))
    }

    // ── getOrNull ────────────────────────────────────────────────

    @Test
    fun `should return value when Success`() {
        val result = Result.Success(42)
        assertEquals(42, result.getOrNull())
    }

    @Test
    fun `should return null when Failure`() {
        val result: Result<Int, String> = Result.Failure("error")
        assertNull(result.getOrNull())
    }

    // ── Error propagation ────────────────────────────────────────

    @Test
    fun `should not throw when mapping over Failure`() {
        val result: Result<Int, String> = Result.Failure("error")
        result.map { it * 2 }
    }

    @Test
    fun `should preserve original Failure through multiple map calls`() {
        val result: Result<Int, String> = Result.Failure("original error")
        val final = result
            .map { it * 2 }
            .map { it + 1 }
            .map { it.toString() }

        assertEquals(Result.Failure("original error"), final)
    }

    @Test
    fun `should preserve original Failure through multiple flatMap calls`() {
        val result: Result<Int, String> = Result.Failure("original error")
        val final = result
            .flatMap { Result.Success(it * 2) }
            .flatMap { Result.Success(it + 1) }

        assertEquals(Result.Failure("original error"), final)
    }

    // ── Equality ─────────────────────────────────────────────────

    @Test
    fun `should be equal when two Success hold same value`() {
        assertEquals(Result.Success(42), Result.Success(42))
    }

    @Test
    fun `should be equal when two Failure hold same error`() {
        assertEquals(Result.Failure("error"), Result.Failure("error"))
    }

    @Test
    fun `should not be equal when Success and Failure`() {
        assertFalse(Result.Success(42) == Result.Failure(42))
    }

    @Test
    fun `should not be equal when Success holds different values`() {
        assertNotEquals(Result.Success(42), Result.Success(99))
    }

    @Test
    fun `should not be equal when Failure holds different errors`() {
        assertNotEquals(Result.Failure("error1"), Result.Failure("error2"))
    }
}