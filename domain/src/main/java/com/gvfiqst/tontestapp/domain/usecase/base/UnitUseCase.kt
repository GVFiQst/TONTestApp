package com.gvfiqst.tontestapp.domain.usecase.base

import com.gvfiqst.tontestapp.domain.datatype.result.OpResult
import com.gvfiqst.tontestapp.domain.datatype.result.withErrorValue
import com.gvfiqst.tontestapp.domain.datatype.result.withSuccessValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


abstract class UnitUseCase<P> {

    protected abstract suspend fun run(params: P): OpResult<Unit>

    operator fun invoke(
        params: P,
        scope: CoroutineScope,
        onSuccess: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        val deferred = scope.async(Dispatchers.IO) {
            try {
                run(params)
            } catch (e: Throwable) {
                OpResult.Error(e)
            }
        }

        scope.launch {
            deferred.await()
                .withSuccessValue(onSuccess)
                .withErrorValue(onError)
        }
    }
}
