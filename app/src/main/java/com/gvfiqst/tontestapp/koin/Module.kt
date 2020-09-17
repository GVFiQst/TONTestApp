package com.gvfiqst.tontestapp.koin

import com.gvfiqst.tontestapp.BuildConfig
import com.gvfiqst.tontestapp.data.api.base.ApiConfig
import com.gvfiqst.tontestapp.domain.misc.Logger
import com.gvfiqst.tontestapp.logger.AppLogger
import org.koin.dsl.module


val appModule = module {

    single { ApiConfig(BuildConfig.API_URL, BuildConfig.API_KEY) }

    single<Logger> { AppLogger() }

}
