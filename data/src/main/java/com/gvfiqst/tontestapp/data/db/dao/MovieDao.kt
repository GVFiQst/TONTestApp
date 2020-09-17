package com.gvfiqst.tontestapp.data.db.dao

import androidx.room.*
import com.gvfiqst.tontestapp.data.db.entity.MovieDto
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun observeAll(): Flow<List<MovieDto>>

    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<MovieDto>

    @Query("SELECT * FROM movies WHERE id=:imdbId")
    fun observeById(imdbId: String): Flow<MovieDto>

    @Query("SELECT * FROM movies WHERE id=:imdbId")
    suspend fun getById(imdbId: String): MovieDto

    @Insert(entity = MovieDto::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieDto)

    @Delete(entity = MovieDto::class)
    suspend fun delete(movie: MovieDto)

    @Update(entity = MovieDto::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(movie: MovieDto)

}
