package com.gvfiqst.tontestapp.presentation.ui.search

import android.os.Bundle
import android.view.View
import com.gvfiqst.tontestapp.presentation.R
import com.gvfiqst.tontestapp.presentation.base.ViewModelFragment
import com.gvfiqst.tontestapp.presentation.ui.search.view.SearchResultView
import com.gvfiqst.tontestapp.presentation.ui.views.MovieInfoViewData
import com.gvfiqst.tontestapp.presentation.utils.observeData
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment(
    override val viewModel: SearchViewModel,
    private val navigation: SearchNavigation
) : ViewModelFragment(R.layout.fragment_search), SearchResultView.Callback {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        subscribe()
    }

    private fun initView() {
        searchResultView.callback = this
    }

    private fun subscribe() {
        viewModel.uiState.observeData(this) {
            searchResultView.setData(it.searchResultViewData)
        }

        viewModel.effect.observeData(this) {

        }
    }

    override fun onSearchResultInputCleared() {
        viewModel.dispatchAction(SearchAction.QueryCleared)
    }

    override fun onSearchResultInputEdited() {
        viewModel.dispatchAction(SearchAction.QueryEdited)
    }

    override fun onSearchResultInputChanged(text: String) {
        viewModel.dispatchAction(SearchAction.Search(text))
    }

    override fun onItemSelected(item: MovieInfoViewData) {
    }

}
