package com.gvfiqst.tontestapp.domain.koin

import com.gvfiqst.tontestapp.domain.usecase.SearchMoviesUseCase
import org.koin.dsl.module


val domainModule = module {

    single { SearchMoviesUseCase(get()) }

}
