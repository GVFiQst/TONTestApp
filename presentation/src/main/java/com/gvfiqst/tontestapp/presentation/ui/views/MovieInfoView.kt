package com.gvfiqst.tontestapp.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.gvfiqst.tontestapp.presentation.R
import kotlinx.android.synthetic.main.view_movie_info.view.*
import kotlin.properties.Delegates


class MovieInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val glide by lazy { Glide.with(this) }

    var callback by Delegates.observable<Callback?>(null) { _, _, callback -> onCallbackChange(callback) }

    init {
        View.inflate(context, R.layout.view_movie_info, this)

        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun onDetachedFromWindow() {
        callback = null
        super.onDetachedFromWindow()
    }

    fun setData(model: MovieInfoViewData) {
        txtTitle.text = model.title
        txtDescription.text = "${model.type}, ${model.year}"

        imgPoster.setImageResource(R.color.primaryColor)
        if (model.posterUrl.isNotBlank()) {
            glide.load(model.posterUrl)
                .apply(RequestOptions.centerCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgPoster)
        }
    }

    private fun onCallbackChange(callback: Callback?) {
        imgPoster?.setOnClickListener(
            callback?.let { OnClickListener { callback.onMovieInfoClicked() } }
        )
    }

    interface Callback {

        fun onMovieInfoClicked()

    }

}
