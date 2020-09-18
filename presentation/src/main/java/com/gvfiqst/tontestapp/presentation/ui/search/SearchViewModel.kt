package com.gvfiqst.tontestapp.presentation.ui.search

import com.gvfiqst.tontestapp.domain.datatype.exception.SearchException
import com.gvfiqst.tontestapp.domain.model.MovieInfo
import com.gvfiqst.tontestapp.domain.model.MovieType
import com.gvfiqst.tontestapp.domain.usecase.SearchMoviesParams
import com.gvfiqst.tontestapp.domain.usecase.SearchMoviesUseCase
import com.gvfiqst.tontestapp.presentation.base.ViewModel
import com.gvfiqst.tontestapp.presentation.ui.views.MovieInfoViewData
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
            is SearchAction.QueryEdited -> clearResults()
            is SearchAction.QueryCleared -> clearResults(false)
            is SearchAction.Search -> search(action.text)
        }
    }

    private fun search(query: String) {
        state.copy(
            searchResultViewData = state.searchResultViewData.copy(
                isLoading = true
            )
        ).emit()

        val params = SearchMoviesParams(query)
        searchMoviesUseCase(params, ::onSearchMoviesResult, ::onSearchMoviesError)
    }

    private fun onSearchMoviesError(error: Throwable) {
        if (error is SearchException && (error.reason == SearchException.Reason.MovieNotFound
            || error.reason == SearchException.Reason.TooManyResults)) {
            clearResults(false)
        } else {
            handleError(error)
        }
    }

    private fun onSearchMoviesResult(result: List<MovieInfo>) {
        logger.d(tag, "onSearchMoviesResult -> $result")

        val movieInfoViewData = result.map {
            val type = when (it.type) {
                // TODO: Vlad Greschuk 18/09/2020 change with enums
                MovieType.Movie -> "movie"
                MovieType.Series -> "series"
                MovieType.Unknown -> "unknown"
            }

            MovieInfoViewData(it.imdbId, it.title, it.year, type, it.poster)
        }

        state.copy(
            searchResultViewData = state.searchResultViewData.copy(
                isLoading = false,
                items = movieInfoViewData
            )
        ).emit()
    }

    private fun clearResults(isLoading: Boolean? = null) {
        val loading = isLoading ?: state.searchResultViewData.isLoading
        state.searchResultViewData.apply {
            if (this.isLoading == loading && items.isEmpty()) {
                return
            }
        }

        state.copy(
            searchResultViewData = state.searchResultViewData.copy(
                isLoading = loading,
                items = emptyList()
            )
        ).emit()
    }

}


sealed class SearchAction {

    object QueryEdited : SearchAction()

    object QueryCleared : SearchAction()

    data class Search(val text: String) : SearchAction()

}

sealed class SearchEffect
