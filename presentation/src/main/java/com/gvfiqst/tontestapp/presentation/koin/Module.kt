package com.gvfiqst.tontestapp.presentation.koin

import com.gvfiqst.tontestapp.presentation.main.MainActivity
import com.gvfiqst.tontestapp.presentation.routing.MainNavigation
import com.gvfiqst.tontestapp.presentation.routing.MainNavigationImpl
import com.gvfiqst.tontestapp.presentation.ui.search.SearchFragment
import com.gvfiqst.tontestapp.presentation.ui.search.SearchNavigation
import com.gvfiqst.tontestapp.presentation.ui.search.SearchViewModel
import kotlinx.coroutines.newSingleThreadContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext


val presentationModule = module {

    single<CoroutineContext> { newSingleThreadContext("Presentation thread") }

    scope<MainActivity> {
        scoped { MainNavigationImpl(get()) }
        scoped<MainNavigation> { get<MainNavigationImpl>() }

        scoped<SearchNavigation> { get<MainNavigationImpl>() }
        viewModel { SearchViewModel(get(), get()) }
        fragment { SearchFragment(get(), get()) }
    }
}
