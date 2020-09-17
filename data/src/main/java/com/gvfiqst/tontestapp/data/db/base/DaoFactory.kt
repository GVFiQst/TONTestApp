package com.gvfiqst.tontestapp.data.db.base

import com.gvfiqst.tontestapp.data.db.dao.MovieDao


object DaoFactory {

    fun creteMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

}
