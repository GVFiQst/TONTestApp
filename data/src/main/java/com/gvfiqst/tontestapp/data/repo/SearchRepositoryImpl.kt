package com.gvfiqst.tontestapp.data.repo

import com.gvfiqst.tontestapp.data.api.omdb.OmdbSearchApi
import com.gvfiqst.tontestapp.data.mapper.MovieMappers
import com.gvfiqst.tontestapp.data.util.apiCall
import com.gvfiqst.tontestapp.domain.datatype.exception.SearchException
import com.gvfiqst.tontestapp.domain.datatype.result.OpResult
import com.gvfiqst.tontestapp.domain.datatype.result.asErrorResult
import com.gvfiqst.tontestapp.domain.datatype.result.asSuccessResult
import com.gvfiqst.tontestapp.domain.datatype.result.flatMap
import com.gvfiqst.tontestapp.domain.model.Movie
import com.gvfiqst.tontestapp.domain.repo.SearchRepository
import com.gvfiqst.tontestapp.domain.util.Logger


class SearchRepositoryImpl(
    private val searchApi: OmdbSearchApi,
    private val logger: Logger
) : SearchRepository {

    override suspend fun findMovies(query: String): OpResult<List<Movie>> {
        return apiCall { searchApi.searchMovie(query) }
            .flatMap { result ->
                val search = result.search
                val error = result.error

                when {
                    !search.isNullOrEmpty() -> {
                        MovieMappers.searchFromDto.map(result)
                            .asSuccessResult()
                    }

                    !error.isNullOrBlank() -> {
                        SearchException(error)
                            .asErrorResult()
                    }

                    else -> {
                        SearchException("Unknown exception")
                            .apply { logger.e(TAG, "Unknown exception with search $result", this) }
                            .asErrorResult()
                    }
                }
            }
    }

    companion object {
        private const val TAG = "SearchRepository"
    }

}
