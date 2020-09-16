package com.gvfiqst.tontestapp.domain.usecase.base

import com.gvfiqst.tontestapp.domain.datatype.result.OpResult
import com.gvfiqst.tontestapp.domain.datatype.result.withErrorValue
import com.gvfiqst.tontestapp.domain.datatype.result.withSuccessValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


abstract class NoParamsUnitUseCase {

    protected abstract suspend fun run(): OpResult<Unit>

    operator fun invoke(
        scope: CoroutineScope,
        onSuccess: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        val deffered = scope.async(Dispatchers.IO) { run() }
        scope.launch {
            deffered.await()
                .withSuccessValue(onSuccess)
                .withErrorValue(onError)
        }
    }

}
