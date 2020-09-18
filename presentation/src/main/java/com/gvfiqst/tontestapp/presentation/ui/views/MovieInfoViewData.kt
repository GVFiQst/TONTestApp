package com.gvfiqst.tontestapp.presentation.ui.views


data class MovieInfoViewData(
    val id: String,
    val title: CharSequence,
    val year: CharSequence,
    val type: MovieType,
    val posterUrl: String
) {

    enum class MovieType {
        Movie, Series, Unknown
    }

}
