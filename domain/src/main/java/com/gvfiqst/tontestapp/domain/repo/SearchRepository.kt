package com.gvfiqst.tontestapp.domain.repo

import com.gvfiqst.tontestapp.domain.model.Movie


interface SearchRepository {

    suspend fun findMovies(query: String): List<Movie>

}
