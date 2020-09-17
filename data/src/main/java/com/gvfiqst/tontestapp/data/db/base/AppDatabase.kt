package com.gvfiqst.tontestapp.data.db.base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gvfiqst.tontestapp.data.db.dao.MovieDao
import com.gvfiqst.tontestapp.data.db.entity.MovieDto


@Database(entities = [MovieDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}
