package com.gvfiqst.tontestapp.domain.repo

import com.gvfiqst.tontestapp.domain.datatype.result.OpResult
import com.gvfiqst.tontestapp.domain.model.MovieInfo


interface SearchRepository {

    suspend fun findMovies(query: String): OpResult<List<MovieInfo>>

}
