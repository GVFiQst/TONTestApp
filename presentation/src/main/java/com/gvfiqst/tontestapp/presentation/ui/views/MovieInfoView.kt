package com.gvfiqst.tontestapp.presentation.ui.views

import android.content.Context
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.gvfiqst.tontestapp.presentation.R
import com.gvfiqst.tontestapp.presentation.utils.singleClickListener
import kotlinx.android.synthetic.main.view_movie_info.view.*
import kotlin.properties.Delegates


class MovieInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val glide by lazy { Glide.with(this) }

    var callback by Delegates.observable<Callback?>(null) { _, _, callback -> onCallbackChange(callback) }

    init {
        View.inflate(context, R.layout.view_movie_info, this)

        orientation = VERTICAL
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun onDetachedFromWindow() {
        callback = null
        super.onDetachedFromWindow()
    }

    fun setData(model: MovieInfoViewData) {
        txtTitle.text = model.title
        txtDescription.text = getDescriptionText(model.type, model.year)

        imgPoster.setImageResource(R.color.primaryColor)
        if (model.posterUrl.isNotBlank()) {
            glide.load(model.posterUrl)
                .apply(RequestOptions.centerCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgPoster)
        }
    }

    private fun onCallbackChange(callback: Callback?) {
        if (callback != null) {
            imgPoster?.singleClickListener(600L, callback::onMovieInfoClicked)
        } else {
            imgPoster.setOnClickListener(null)
        }
    }

    private fun getDescriptionText(type: MovieInfoViewData.MovieType, year: CharSequence): CharSequence {
        if (type == MovieInfoViewData.MovieType.Unknown) {
            return year
        }

        val resId = when (type) {
            MovieInfoViewData.MovieType.Movie -> R.string.movie_info_type_movie
            MovieInfoViewData.MovieType.Series -> R.string.movie_info_type_series
            MovieInfoViewData.MovieType.Unknown -> throw AssertionError()
        }
        val typeText = context.getText(resId)

        if (year.isBlank()) {
            return typeText
        }

        return SpannableStringBuilder()
            .append(typeText)
            .append(", ")
            .append(year)
    }

    interface Callback {

        fun onMovieInfoClicked()

    }

}
