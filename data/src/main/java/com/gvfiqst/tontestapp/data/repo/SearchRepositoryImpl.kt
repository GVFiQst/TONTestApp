package com.gvfiqst.tontestapp.data.repo

import com.gvfiqst.tontestapp.data.api.omdb.OmdbSearchApi
import com.gvfiqst.tontestapp.data.mapper.MovieMappers
import com.gvfiqst.tontestapp.data.util.OmdbPosterUriTransform
import com.gvfiqst.tontestapp.data.util.apiCall
import com.gvfiqst.tontestapp.domain.datatype.exception.SearchException
import com.gvfiqst.tontestapp.domain.datatype.result.*
import com.gvfiqst.tontestapp.domain.model.MovieInfo
import com.gvfiqst.tontestapp.domain.repo.SearchRepository
import com.gvfiqst.tontestapp.domain.util.Logger


class SearchRepositoryImpl(
    private val searchApi: OmdbSearchApi,
    private val logger: Logger
) : SearchRepository {

    override suspend fun findMovies(query: String): OpResult<List<MovieInfo>> {
        return apiCall { searchApi.searchMovie(query) }
            .flatMap { result ->
                val search = result.search
                val error = result.error

                when {
                    !search.isNullOrEmpty() -> {
                        MovieMappers.searchFromResponse.map(result)
                            .asSuccessResult()
                            .listMap { it.copy(poster = OmdbPosterUriTransform.transform(it.poster)) }
                    }

                    !error.isNullOrBlank() -> {
                        val reason = when (error) {
                            HARDCODED_REASON_MANY_RESULTS -> SearchException.Reason.TooManyResults
                            HARDCODED_REASON_NOT_FOUND -> SearchException.Reason.MovieNotFound
                            else -> SearchException.Reason.Unknown
                        }

                        SearchException(reason, error)
                            .asErrorResult()
                    }

                    else -> {
                        SearchException(SearchException.Reason.Unknown, "Unknown exception")
                            .apply { logger.e(TAG, "Unknown exception with search $result", this) }
                            .asErrorResult()
                    }
                }
            }
    }

    companion object {
        private const val TAG = "SearchRepository"
        private const val HARDCODED_REASON_NOT_FOUND = "Movie not found!"
        private const val HARDCODED_REASON_MANY_RESULTS = "Too many results."
    }

}
