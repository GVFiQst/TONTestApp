package com.gvfiqst.tontestapp.data.api

import com.gvfiqst.tontestapp.data.api.omdb.OmdbApi
import com.gvfiqst.tontestapp.data.api.omdb.OmdbSearchApi
import retrofit2.Retrofit
import retrofit2.create


object ApiFactory {

    fun createOmdbApi(retrofit: Retrofit): OmdbApi {
        return retrofit.create()
    }

    fun createOmdbSearchApi(retrofit: Retrofit): OmdbSearchApi {
        return retrofit.create()
    }

}
