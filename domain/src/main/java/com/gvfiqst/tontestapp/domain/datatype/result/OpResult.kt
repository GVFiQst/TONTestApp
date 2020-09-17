package com.gvfiqst.tontestapp.domain.datatype.result


sealed class OpResult<out T> {

    data class Success<out T>(val payload: T): OpResult<T>()

    data class Error(val error: Throwable): OpResult<Nothing>()

    companion object {
        private val unitResult = Success(Unit)

        fun Unit() = unitResult
    }
}
