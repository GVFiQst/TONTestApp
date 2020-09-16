package com.gvfiqst.tontestapp.data.api.omdb

import com.gvfiqst.tontestapp.data.api.omdb.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface OmdbSearchApi {

    @GET("/?page=1")
    suspend fun searchMovie(@Query("s") query: String): SearchResponse

}
