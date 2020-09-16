package com.gvfiqst.tontestapp.domain.datatype.result


sealed class OpResult<out T> {

    data class Success<out T>(val payload: T): OpResult<T>()

    data class Error(val error: Throwable): OpResult<Nothing>()

    companion object {
        private val unitResult = Success(Unit)

        fun Unit() = unitResult
    }
}

inline fun OpResult<Unit>.withSuccessValue(block: () -> Unit): OpResult<Unit> {
    if (this is OpResult.Success) {
        block()
    }

    return this
}

inline fun <T> OpResult<T>.withSuccessValue(block: (T) -> Unit): OpResult<T> {
    if (this is OpResult.Success) {
        block(payload)
    }

    return this
}

inline fun <T> OpResult<T>.withErrorValue(block: (Throwable) -> Unit): OpResult<T> {
    if (this is OpResult.Error) {
        block(error)
    }

    return this
}

inline fun <T, R> OpResult<T>.map(block: (T) -> R): OpResult<R> {
    return when (this) {
        is OpResult.Success -> OpResult.Success(block(payload))
        is OpResult.Error -> this
    }
}

inline fun <T, R> OpResult<T>.flatMap(block: (T) -> OpResult<R>): OpResult<R> {
    return when (this) {
        is OpResult.Success -> block(payload)
        is OpResult.Error -> this
    }
}

inline fun <T> OpResult<T>.onErrorReturn(block: (Throwable) -> OpResult<T>): OpResult<T> {
    if (this is OpResult.Error) {
        return block(error)
    }

    return this
}
