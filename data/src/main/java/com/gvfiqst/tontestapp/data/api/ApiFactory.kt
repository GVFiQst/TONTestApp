package com.gvfiqst.tontestapp.data.api

import com.gvfiqst.tontestapp.data.api.omdb.OmdbApi
import retrofit2.Retrofit
import retrofit2.create


object ApiFactory {

    fun createOmdbApi(retrofit: Retrofit): OmdbApi {
        return retrofit.create()
    }

}
