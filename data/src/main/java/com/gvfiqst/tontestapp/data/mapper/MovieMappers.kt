package com.gvfiqst.tontestapp.data.mapper

import com.gvfiqst.tontestapp.data.api.omdb.model.MovieResponse
import com.gvfiqst.tontestapp.data.api.omdb.model.SearchResponse
import com.gvfiqst.tontestapp.domain.mappings.Mapper
import com.gvfiqst.tontestapp.domain.model.Movie


object MovieMappers {

    val movieFromDto = object : Mapper<MovieResponse, Movie> {
        override fun map(model: MovieResponse) = Movie(
            model.imdbId ?: "",
            model.title ?: ""
        )
    }

    val searchFromDto = object : Mapper<SearchResponse, List<Movie>> {
        override fun map(model: SearchResponse): List<Movie> {
            return model.search.orEmpty().map(movieFromDto::map)
        }
    }

}
