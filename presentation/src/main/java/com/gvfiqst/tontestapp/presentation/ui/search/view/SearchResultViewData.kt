package com.gvfiqst.tontestapp.presentation.ui.search.view

import com.gvfiqst.tontestapp.presentation.ui.views.MovieInfoViewData


data class SearchResultViewData(
    val isLoading: Boolean = false,
    val items: List<MovieInfoViewData> = emptyList()
)
