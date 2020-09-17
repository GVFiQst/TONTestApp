package com.gvfiqst.tontestapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movies")
data class MovieDto(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val imdbId: String,
    @ColumnInfo(name = "title")
    val title: String? = null
)
