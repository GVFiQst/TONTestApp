package com.gvfiqst.tontestapp.domain.usecase.base

import com.gvfiqst.tontestapp.domain.datatype.result.OpResult
import com.gvfiqst.tontestapp.domain.datatype.result.withErrorValue
import com.gvfiqst.tontestapp.domain.datatype.result.withSuccessValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


abstract class NoParamsUseCase<R> {

    protected abstract suspend fun run(): OpResult<R>

    operator fun invoke(
        scope: CoroutineScope,
        onSuccess: (R) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        val deferred = scope.async(Dispatchers.IO) {
            try {
                run()
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
