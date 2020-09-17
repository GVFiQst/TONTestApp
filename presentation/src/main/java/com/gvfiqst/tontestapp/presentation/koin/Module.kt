package com.gvfiqst.tontestapp.presentation.koin

import kotlinx.coroutines.newSingleThreadContext
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext


val presentationModule = module {

    single<CoroutineContext> { newSingleThreadContext("Presentation thread") }

}
