package com.gvfiqst.tontestapp.data.repo

import android.util.Log
import com.gvfiqst.tontestapp.data.api.omdb.OmdbSearchApi
import com.gvfiqst.tontestapp.data.mapper.MovieMappers
import com.gvfiqst.tontestapp.domain.datatype.exception.SearchException
import com.gvfiqst.tontestapp.domain.model.Movie
import com.gvfiqst.tontestapp.domain.repo.SearchRepository


class SearchRepositoryImpl(
    private val searchApi: OmdbSearchApi
) : SearchRepository {

    override suspend fun findMovies(query: String): List<Movie> {
        val search = searchApi.searchMovie(query)

        if (!search.search.isNullOrEmpty()) {
            return MovieMappers.searchFromDto.map(search)
        }

        val error = search.error
        if (!error.isNullOrBlank()) {
            throw SearchException(error)
        }

        val e = SearchException("Unknown exception")
        Log.e(TAG, "Unknown exception with search $search", e)
        throw e
    }

    companion object {
        private const val TAG = "SearchRepository"
    }

}
