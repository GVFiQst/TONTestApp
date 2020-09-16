package com.gvfiqst.tontestapp.domain.usecase

import com.gvfiqst.tontestapp.domain.datatype.result.OpResult
import com.gvfiqst.tontestapp.domain.model.Movie
import com.gvfiqst.tontestapp.domain.repo.SearchRepository
import com.gvfiqst.tontestapp.domain.usecase.base.UseCase


data class SearchMoviesParams(val query: String)

class SearchMoviesUseCase(
    private val searchRepository: SearchRepository
) : UseCase<SearchMoviesParams, List<Movie>>() {

    override suspend fun run(params: SearchMoviesParams): OpResult<List<Movie>> {
        val result = searchRepository.findMovies(params.query)
        return OpResult.Success(result)
    }

}
