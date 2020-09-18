package com.gvfiqst.tontestapp.presentation.ui.search.view

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.gvfiqst.tontestapp.presentation.R
import com.gvfiqst.tontestapp.presentation.base.adapter.SingleViewAdapter
import com.gvfiqst.tontestapp.presentation.ui.views.MovieInfoView
import com.gvfiqst.tontestapp.presentation.ui.views.MovieInfoViewData
import com.gvfiqst.tontestapp.presentation.utils.dp
import com.gvfiqst.tontestapp.presentation.utils.itemDecoration.GridLayoutMarginItemDecoration
import com.gvfiqst.tontestapp.presentation.utils.onTextChanged
import kotlinx.android.synthetic.main.view_search_result.view.*
import kotlin.properties.Delegates


class SearchResultView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    var callback by Delegates.observable<Callback?>(null) { _, _, callback -> onCallbackChange(callback) }

    private val adapter by lazy(::createAdapter)
    private var textWatchers = mutableListOf<TextWatcher>()

    init {
        View.inflate(context, R.layout.view_search_result, this)

        val spanCount = 3
        searchResultRecyclerView.layoutManager = GridLayoutManager(context, spanCount)
        searchResultRecyclerView.addItemDecoration(GridLayoutMarginItemDecoration(spanCount, 8.dp(context)))
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        doOnLayout {
            val height = searchBar.height
            searchResultRecyclerView.setPadding(0, height, 0, 0)
            pbLoading.updateLayoutParams<LayoutParams> {
                topMargin = height + 16.dp(context)
            }
        }
    }

    override fun onDetachedFromWindow() {
        callback = null
        super.onDetachedFromWindow()
    }

    fun setData(data: SearchResultViewData) {
        if (searchResultRecyclerView.adapter == null) {
            searchResultRecyclerView.adapter = adapter
        }

        if (!data.isLoading) {
            if (data.items.isEmpty()) {
                adapter.clear()
            } else {
                adapter.setNewData(data.items)
            }
        }

        val transition = Fade()
            .addTarget(pbLoading)
            .addTarget(searchResultRecyclerView)
        TransitionManager.beginDelayedTransition(this, transition)

        pbLoading.isVisible = data.isLoading
        searchResultRecyclerView.isVisible = !data.isLoading
    }

    private fun createAdapter() = SingleViewAdapter.create<MovieInfoView, MovieInfoViewData>()
        .createView { parent -> MovieInfoView(parent.context) }
        .bindView { view, data, _ ->
            view.callback = object : MovieInfoView.Callback {
                override fun onMovieInfoClicked() {
                    callback?.onItemSelected(data)
                }
            }
            view.setData(data)
        }
        .build()

    private fun onCallbackChange(callback: Callback?) {
        val editText = edtSearch ?: return

        if (callback == null) {
            val list = ArrayList(textWatchers)
            textWatchers.clear()

            for (w in list) {
                editText.removeTextChangedListener(w)
            }
        } else {
            textWatchers.add(editText.onTextChanged(500) {
                if (it.isNotBlank()) {
                    this.callback?.onSearchResultInputChanged(it)
                }
            })
            textWatchers.add(editText.onTextChanged {
                if (it.isBlank()) {
                    this.callback?.onSearchResultInputCleared()
                } else {
                    this.callback?.onSearchResultInputEdited()
                }
            })
        }
    }

    interface Callback {

        fun onSearchResultInputCleared()

        fun onSearchResultInputEdited()

        fun onSearchResultInputChanged(text: String)

        fun onItemSelected(item: MovieInfoViewData)

    }

}
