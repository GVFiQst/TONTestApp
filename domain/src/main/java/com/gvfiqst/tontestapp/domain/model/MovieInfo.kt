package com.gvfiqst.tontestapp.domain.model


data class MovieInfo (
    val imdbId: String,
    val title: String,
    val year: String,
    val type: MovieType,
    val poster: String
)
