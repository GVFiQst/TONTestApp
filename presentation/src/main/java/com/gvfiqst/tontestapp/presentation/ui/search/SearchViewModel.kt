package com.gvfiqst.tontestapp.presentation.ui.search

import com.gvfiqst.tontestapp.domain.model.MovieInfo
import com.gvfiqst.tontestapp.domain.usecase.SearchMoviesParams
import com.gvfiqst.tontestapp.domain.usecase.SearchMoviesUseCase
import com.gvfiqst.tontestapp.presentation.base.ViewModel
import kotlin.coroutines.CoroutineContext


class SearchViewModel(
    baseCoroutineContext: CoroutineContext,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel<SearchState, SearchAction, SearchEffect>(
    baseCoroutineContext,
    SearchState()
) {

    override fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.Init -> {

            }
            is SearchAction.Search -> {
                val params = SearchMoviesParams(action.text)
                searchMoviesUseCase(params, ::onSearchMoviesResult)
            }
        }
    }

    private fun onSearchMoviesResult(result: List<MovieInfo>) {
        logger.d(tag, "onSearchMoviesResult -> $result")
    }

}


sealed class SearchAction {

    object Init : SearchAction()

    data class Search(val text: String) : SearchAction()

}

sealed class SearchEffect
