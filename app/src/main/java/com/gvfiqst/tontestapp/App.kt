package com.gvfiqst.tontestapp

import android.app.Application
import com.gvfiqst.tontestapp.data.koin.dataModule
import com.gvfiqst.tontestapp.domain.koin.domainModule
import com.gvfiqst.tontestapp.presentation.koin.presentationModule
import com.gvfiqst.tontestapp.koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoinModules()
    }

    private fun initKoinModules() {
        startKoin {
            androidContext(this@App)
            fragmentFactory()
            modules(
                appModule,
                dataModule,
                domainModule,
                presentationModule
            )
        }
    }
}
