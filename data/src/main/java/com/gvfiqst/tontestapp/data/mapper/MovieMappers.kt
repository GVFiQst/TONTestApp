package com.gvfiqst.tontestapp.data.mapper

import com.gvfiqst.tontestapp.data.api.omdb.model.MovieInfoResponse
import com.gvfiqst.tontestapp.data.api.omdb.model.MovieResponse
import com.gvfiqst.tontestapp.data.api.omdb.model.SearchResponse
import com.gvfiqst.tontestapp.domain.datatype.exception.MapperException
import com.gvfiqst.tontestapp.domain.mappings.Mapper
import com.gvfiqst.tontestapp.domain.model.Movie
import com.gvfiqst.tontestapp.domain.model.MovieInfo
import com.gvfiqst.tontestapp.domain.model.MovieType


object MovieMappers {

    val movieFromResponse = object : Mapper<MovieResponse, Movie> {
        override fun map(model: MovieResponse) = Movie(
            model.imdbId ?: "",
            model.title ?: ""
        )
    }

    val movieInfoFromResponse = object : Mapper<MovieInfoResponse, MovieInfo> {
        private val TYPE_MOVIE = "movie"
        private val TYPE_SERIES = "series"

        override fun map(model: MovieInfoResponse): MovieInfo {
            val type = when (model.type) {
                TYPE_MOVIE -> MovieType.Movie
                TYPE_SERIES -> MovieType.Series
                else -> MovieType.Unknown
            }

            return MovieInfo(
                model.imdbId ?: throw MapperException("movie info has empty imbdID: $model"),
                model.title ?: "",
                model.year ?: "",
                type,
                model.poster ?: ""
            )
        }
    }

    val searchFromResponse = object : Mapper<SearchResponse, List<MovieInfo>> {
        override fun map(model: SearchResponse): List<MovieInfo> {
            return model.search.orEmpty().map(movieInfoFromResponse::map)
        }
    }

}
