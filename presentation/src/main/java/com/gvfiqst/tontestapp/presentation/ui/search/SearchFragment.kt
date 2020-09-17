package com.gvfiqst.tontestapp.presentation.ui.search

import android.os.Bundle
import android.view.View
import com.gvfiqst.tontestapp.presentation.R
import com.gvfiqst.tontestapp.presentation.base.ViewModelFragment
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment(
    override val viewModel: SearchViewModel,
    private val navigation: SearchNavigation
) : ViewModelFragment(R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dispatchAction(SearchAction.Init)

        btnSearch.setOnClickListener {
            val text = edtSearch.text.toString()
            if (text.isNotBlank()) {
                viewModel.dispatchAction(SearchAction.Search(text))
            }
        }
    }

}
