package com.gvfiqst.tontestapp.domain.repo

import com.gvfiqst.tontestapp.domain.model.Movie


interface MoviesRepository {

    fun getMovieById(id: String): Movie

}
