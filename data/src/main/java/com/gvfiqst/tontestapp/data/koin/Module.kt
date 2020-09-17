package com.gvfiqst.tontestapp.data.koin

import com.gvfiqst.tontestapp.data.api.ApiFactory
import com.gvfiqst.tontestapp.data.api.base.GsonFactory
import com.gvfiqst.tontestapp.data.api.base.OkHttpFactory
import com.gvfiqst.tontestapp.data.api.base.RetrofitFactory
import com.gvfiqst.tontestapp.data.db.base.AppDatabase
import com.gvfiqst.tontestapp.data.db.base.AppDatabaseFactory
import com.gvfiqst.tontestapp.data.db.base.DaoFactory
import com.gvfiqst.tontestapp.data.db.dao.MovieDao
import com.gvfiqst.tontestapp.data.repo.SearchRepositoryImpl
import com.gvfiqst.tontestapp.domain.repo.SearchRepository
import org.koin.dsl.module


val dataModule = module {

    single { GsonFactory.create() }

    single { OkHttpFactory.create(get()) }

    single { RetrofitFactory.create(get(), get(), get()) }

    single { ApiFactory.createOmdbApi(get()) }

    single { AppDatabaseFactory.create(get(), get()) }

    single { DaoFactory.creteMovieDao(get()) }

    single<SearchRepository> { SearchRepositoryImpl(get(), get()) }

}
