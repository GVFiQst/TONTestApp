package com.gvfiqst.tontestapp.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gvfiqst.tontestapp.domain.usecase.base.NoParamsUnitUseCase
import com.gvfiqst.tontestapp.domain.usecase.base.NoParamsUseCase
import com.gvfiqst.tontestapp.domain.usecase.base.UnitUseCase
import com.gvfiqst.tontestapp.domain.usecase.base.UseCase
import com.gvfiqst.tontestapp.domain.util.Logger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.coroutines.CoroutineContext


abstract class ViewModel<State, Action, Effect>(
    baseCoroutineContext: CoroutineContext,
    protected var state: State
) : androidx.lifecycle.ViewModel(), CoroutineScope, KoinComponent {

    protected val tag by lazy { this.javaClass.simpleName }

    private val job = SupervisorJob()

    private val _uiState = MutableLiveData<State>(state)
    val uiState: LiveData<State> get() = _uiState

    private val _effect = MutableLiveData<Effect>()
    val effect: LiveData<Effect> get() = _effect

    private var prevAction: Action? = null
    private val actions = Channel<Action>(Channel.BUFFERED)

    protected val logger: Logger by inject()

    override val coroutineContext = baseCoroutineContext + job + CoroutineExceptionHandler { _, e -> handleError(e) }

    init {
        launch {
            actions.consumeEach { action ->
                if (action != prevAction) {
                    prevAction = action
                    onAction(action)
                }
            }
        }.invokeOnCompletion {
            actions.close()
        }
    }

    protected abstract fun onAction(action: Action)

    fun dispatchDestroy() {
        launch {
            onDestroy()
        }.invokeOnCompletion {
            job.cancel()
        }
    }

    open fun onDestroy() {
    }

    fun dispatchAction(action: Action) {
        actions.offer(action)
    }


    protected fun handleError(error: Throwable) {
        logger.e(tag, "handleError: ", RuntimeException(error))
    }

    protected fun State.emit() {
        state = this
        _uiState.postValue(this)
    }

    protected fun Effect.effect() {
        _effect.postValue(this)
    }

    protected operator fun <P, R> UseCase<P, R>.invoke(
        params: P,
        onSuccess: (R) -> Unit = {},
        onError: (Throwable) -> Unit = ::handleError
    ) {
        invoke(params, this@ViewModel, onSuccess, onError)
    }

    protected operator fun <P, R> UseCase<P, R>.invoke(
        params: P,
        onSuccess: (R) -> Unit = {}
    ) {
        invoke(params, this@ViewModel, onSuccess, ::handleError)
    }

    protected operator fun NoParamsUnitUseCase.invoke(
        onSuccess: () -> Unit = {},
        onError: (Throwable) -> Unit = ::handleError
    ) {
        invoke(this@ViewModel, onSuccess, onError)
    }

    protected operator fun NoParamsUnitUseCase.invoke(
        onSuccess: () -> Unit = {}
    ) {
        invoke(this@ViewModel, onSuccess, ::handleError)
    }

    protected operator fun <R> NoParamsUseCase<R>.invoke(
        onSuccess: (R) -> Unit = {},
        onError: (Throwable) -> Unit = ::handleError
    ) {
        invoke(this@ViewModel, onSuccess, onError)
    }

    protected operator fun <R> NoParamsUseCase<R>.invoke(
        onSuccess: (R) -> Unit = {}
    ) {
        invoke(this@ViewModel, onSuccess, ::handleError)
    }

    protected operator fun <P> UnitUseCase<P>.invoke(
        params: P,
        onSuccess: () -> Unit = {},
        onError: (Throwable) -> Unit = ::handleError
    ) {
        invoke(params, this@ViewModel, onSuccess, onError)
    }

    protected operator fun <P> UnitUseCase<P>.invoke(
        params: P,
        onSuccess: () -> Unit = {}
    ) {
        invoke(params, this@ViewModel, onSuccess, ::handleError)
    }

}
