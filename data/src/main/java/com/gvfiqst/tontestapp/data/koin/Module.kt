package com.gvfiqst.tontestapp.data.koin

import com.gvfiqst.tontestapp.data.api.ApiFactory
import com.gvfiqst.tontestapp.data.api.base.GsonFactory
import com.gvfiqst.tontestapp.data.api.base.OkHttpFactory
import com.gvfiqst.tontestapp.data.api.base.RetrofitFactory
import org.koin.dsl.module


val dataModule = module {

    single { GsonFactory.create() }

    single { OkHttpFactory.create(get()) }

    single { RetrofitFactory.create(get(), get(), get()) }

    single { ApiFactory.createOmdbApi(get()) }

}
