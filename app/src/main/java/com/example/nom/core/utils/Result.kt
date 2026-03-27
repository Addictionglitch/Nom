package com.example.nom.core.utils

/**
 * Sealed class wrapping operation outcomes for all network and database operations.
 *
 * Usage:
 * ```
 * when (val result = repository.getSpirit(id)) {
 *     is Result.Success -> handleSpirit(result.data)
 *     is Result.Error -> showError(result.message)
 * }
 * ```
 *
 * Rule: Never catch Exception without logging. All errors must be wrapped
 * in [Error] with a meaningful message for user-facing display.
 */
sealed class Result<out T> {

    /** Operation completed successfully with [data]. */
    data class Success<out T>(val data: T) : Result<T>()

    /** Operation failed with an [exception] and optional user-facing [message]. */
    data class Error(
        val exception: Throwable? = null,
        val message: String? = null
    ) : Result<Nothing>()

    /** True if this is a [Success] result. */
    val isSuccess: Boolean get() = this is Success

    /** True if this is an [Error] result. */
    val isError: Boolean get() = this is Error

    /** Returns the data if [Success], or null if [Error]. */
    fun getOrNull(): T? = when (this) {
        is Success -> data
        is Error -> null
    }

    /** Returns the data if [Success], or [defaultValue] if [Error]. */
    fun getOrDefault(defaultValue: @UnsafeVariance T): T = when (this) {
        is Success -> data
        is Error -> defaultValue
    }

    /** Returns the data if [Success], or throws the exception if [Error]. */
    fun getOrThrow(): T = when (this) {
        is Success -> data
        is Error -> throw exception ?: IllegalStateException(message ?: "Unknown error")
    }

    /**
     * Transforms the data inside a [Success] result.
     * [Error] results pass through unchanged.
     */
    inline fun <R> map(transform: (T) -> R): Result<R> = when (this) {
        is Success -> Success(transform(data))
        is Error -> this
    }

    /**
     * Chains an operation that itself returns a [Result].
     * [Error] results pass through unchanged.
     */
    inline fun <R> flatMap(transform: (T) -> Result<R>): Result<R> = when (this) {
        is Success -> transform(data)
        is Error -> this
    }

    /**
     * Executes [action] if this is a [Success]. Returns this for chaining.
     */
    inline fun onSuccess(action: (T) -> Unit): Result<T> {
        if (this is Success) action(data)
        return this
    }

    /**
     * Executes [action] if this is an [Error]. Returns this for chaining.
     */
    inline fun onError(action: (Error) -> Unit): Result<T> {
        if (this is Error) action(this)
        return this
    }

    companion object {
        /** Wraps a block in a try-catch, returning [Success] or [Error]. */
        @JvmStatic
        inline fun <T> runCatching(block: () -> T): Result<T> = try {
            Success(block())
        } catch (e: Exception) {
            Error(exception = e, message = e.message)
        }
    }
}
