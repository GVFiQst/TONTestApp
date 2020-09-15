package com.gvfiqst.tontestapp.data.api.omdb

import com.gvfiqst.tontestapp.data.api.omdb.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface OmdbApi {

    @GET("/")
    suspend fun getMovieById(@Query("i") id: String): MovieResponse

}
