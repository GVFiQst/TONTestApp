package com.gvfiqst.tontestapp.domain.usecase

import com.gvfiqst.tontestapp.domain.datatype.result.OpResult
import com.gvfiqst.tontestapp.domain.model.MovieInfo
import com.gvfiqst.tontestapp.domain.repo.SearchRepository
import com.gvfiqst.tontestapp.domain.usecase.base.UseCase


data class SearchMoviesParams(val query: String)

class SearchMoviesUseCase(
    private val searchRepository: SearchRepository
) : UseCase<SearchMoviesParams, List<MovieInfo>>() {

    override suspend fun run(params: SearchMoviesParams): OpResult<List<MovieInfo>> {
        return searchRepository.findMovies(params.query)
    }

}
