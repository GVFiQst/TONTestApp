package com.gvfiqst.tontestapp.domain.datatype.result


fun <T> Throwable.asErrorResult(): OpResult<T> = OpResult.Error(this)

fun <T> T.asSuccessResult(): OpResult<T> = OpResult.Success(this)

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

inline fun <T, R> OpResult<List<T>>.listMap(block: (T) -> R): OpResult<List<R>> {
    return map { it.map(block) }
}

inline fun <T> OpResult<T>.onErrorReturn(block: (Throwable) -> OpResult<T>): OpResult<T> {
    if (this is OpResult.Error) {
        return block(error)
    }

    return this
}
