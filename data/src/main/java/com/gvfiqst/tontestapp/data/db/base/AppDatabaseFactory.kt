package com.gvfiqst.tontestapp.data.db.base

import android.content.Context
import androidx.room.Room
import java.util.concurrent.Executors


object AppDatabaseFactory {

    fun create(
        appContext: Context,
        databaseConfig: DatabaseConfig
    ): AppDatabase {
        val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            databaseConfig.name
        )
            .setQueryExecutor(executor)
            .setTransactionExecutor(executor)
            .build()
    }

}
