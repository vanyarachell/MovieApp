package com.vanya.movieapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vanya.movieapp.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavourite(movie: Movie): Long

    @Query("SELECT * FROM movies")
    fun getAllFavourites(): LiveData<List<Movie>>

    @Delete
    suspend fun deleteFavouriteMovie(movie: Movie)
}