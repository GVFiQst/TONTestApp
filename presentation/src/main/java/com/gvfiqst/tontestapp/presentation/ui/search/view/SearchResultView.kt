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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.gvfiqst.tontestapp.domain.util.Logger
import com.gvfiqst.tontestapp.presentation.R
import com.gvfiqst.tontestapp.presentation.base.adapter.SingleViewAdapter
import com.gvfiqst.tontestapp.presentation.ui.views.MovieInfoView
import com.gvfiqst.tontestapp.presentation.ui.views.MovieInfoViewData
import com.gvfiqst.tontestapp.presentation.utils.dp
import com.gvfiqst.tontestapp.presentation.utils.itemDecoration.GridMarginItemDecoration
import com.gvfiqst.tontestapp.presentation.utils.itemDecoration.MarginItemDecorator
import com.gvfiqst.tontestapp.presentation.utils.loggerTag
import com.gvfiqst.tontestapp.presentation.utils.onTextChanged
import kotlinx.android.synthetic.main.view_search_result.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.properties.Delegates


class SearchResultView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes), KoinComponent {

    private val logger by inject<Logger>()

    var callback by Delegates.observable<Callback?>(null) { _, _, callback -> onCallbackChange(callback) }

    private val adapter by lazy(::createAdapter)
    private var textWatchers = mutableListOf<TextWatcher>()

    private var callbacks = mutableListOf<MovieInfoViewCallback>()

    init {
        View.inflate(context, R.layout.view_search_result, this)

        if (attrs != null) {
            parseAttrs(attrs, defStyleAttr, defStyleRes)
        }
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
        clearCallbacks()
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
            val callback = MovieInfoViewCallback(view, this.callback, data)
            callbacks.add(callback)
            view.callback = callback

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

    private fun setupLayoutManager(recyclerView: RecyclerView, spanCount: Int, margin: Int) {
        if (spanCount == 0) {
            recyclerView.layoutManager = LinearLayoutManager(context)

            if (margin > 0) {
                recyclerView.addItemDecoration(MarginItemDecorator(margin))
            }

            return
        }

        recyclerView.layoutManager = GridLayoutManager(context, spanCount)
        if (margin > 0) {
            recyclerView.addItemDecoration(GridMarginItemDecoration(spanCount, margin))
        }
    }

    private fun parseAttrs(attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.SearchResultView, defStyleAttr, defStyleRes)

        try {
            val spanCount = arr.getInt(R.styleable.SearchResultView_srv_spanCount, 0)
            val margin = arr.getDimensionPixelOffset(R.styleable.SearchResultView_srv_itemMargin, 0)
            setupLayoutManager(searchResultRecyclerView, spanCount, margin)
        } catch (e: Throwable) {
            logger.e(loggerTag, "Error parsing attrs: ", e)
        } finally {
            arr.recycle()
        }
    }

    private fun clearCallbacks() {
        callbacks.forEach { it.clear() }
        callbacks.clear()
    }

    private class MovieInfoViewCallback(
        private var source: MovieInfoView? = null,
        private var passToCallback: Callback? = null,
        private var data: MovieInfoViewData? = null
    ) : MovieInfoView.Callback {

        override fun onMovieInfoClicked() {
            source ?: return
            if (source?.callback != this) {
                clear()
                return
            }

            val callback = passToCallback ?: return
            val data = data ?: return
            callback.onItemSelected(data)
        }

        fun clear() {
            if (source?.callback == this) {
                source?.callback = null
            }

            source = null
            passToCallback = null
            data = null
        }

    }

    interface Callback {

        fun onSearchResultInputCleared()

        fun onSearchResultInputEdited()

        fun onSearchResultInputChanged(text: String)

        fun onItemSelected(item: MovieInfoViewData)

    }

}
