package com.gvfiqst.tontestapp.domain.usecase.base

import com.gvfiqst.tontestapp.domain.datatype.result.OpResult
import com.gvfiqst.tontestapp.domain.datatype.result.withErrorValue
import com.gvfiqst.tontestapp.domain.datatype.result.withSuccessValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


abstract class UseCase<P, R> {

    protected abstract suspend fun run(params: P): OpResult<R>

    operator fun invoke(
        params: P,
        scope: CoroutineScope,
        onSuccess: (R) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        val deffered = scope.async(Dispatchers.IO) {
            try {
                run(params)
            } catch (e: Throwable) {
                OpResult.Error(e)
            }
        }
        scope.launch {
            deffered.await()
                .withSuccessValue(onSuccess)
                .withErrorValue(onError)
        }
    }
}
