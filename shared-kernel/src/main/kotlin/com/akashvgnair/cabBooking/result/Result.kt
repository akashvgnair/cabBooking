package com.akashvgnair.cabBooking.result

sealed class Result<out S, out F> {
    data class Success<out S>(val value: S) : Result<S, Nothing>()
    data class Failure<out F>(val error: F) : Result<Nothing, F>()

    fun <B> map(transform: (S) -> B): Result<B, F> =
        when(this) {
            is Failure<F> -> Failure(error);
            is Success<S> -> Success(transform(value))
        }


    fun <B> flatMap(transform: (S) -> Result<B, @UnsafeVariance F>): Result<B, F> =
        when(this) {
            is Failure<F> -> Failure(error)
            is Success<S> -> transform(value)
        }


    fun <B> fold(
        onSuccess: (S) -> B,
        onFailure: (F) -> B
    ): B =
        when(this) {
            is Failure<F> -> onFailure(error)
            is Success<S> -> onSuccess(value)
        }


    fun getOrNull(): S? =
        when(this) {
            is Failure<F> -> null
            is Success<S> -> value
        }


    fun getOrElse(default: @UnsafeVariance S): S =
        when(this) {
            is Failure<F> -> default
            is Success<S> -> value
        }

    val isSuccess: Boolean = this is Success

    val isFailure: Boolean = this is Failure

}