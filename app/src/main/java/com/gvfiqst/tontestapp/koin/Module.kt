package com.gvfiqst.tontestapp.koin

import com.gvfiqst.tontestapp.BuildConfig
import com.gvfiqst.tontestapp.data.api.base.ApiConfig
import org.koin.dsl.module


val appModule = module {

    single { ApiConfig(BuildConfig.API_URL, BuildConfig.API_KEY) }

}
