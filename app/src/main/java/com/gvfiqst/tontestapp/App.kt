package com.gvfiqst.tontestapp

import android.app.Application
import com.gvfiqst.tontestapp.data.koin.dataModule
import com.gvfiqst.tontestapp.domain.koin.domainModule
import com.gvfiqst.tontestapp.koin.appModule
import com.gvfiqst.tontestapp.presentation.koin.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoinModules()
    }

    private fun initKoinModules() {
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger(Level.DEBUG)
            }

            androidContext(this@App)
            modules(
                appModule,
                dataModule,
                domainModule,
                presentationModule
            )
        }
    }
}
