package com.vanya.movieapp.repository

import com.vanya.movieapp.api.RetrofitInstance
import com.vanya.movieapp.db.MovieDatabase
import com.vanya.movieapp.model.Movie

class MovieRepository(private val db: MovieDatabase) {
    suspend fun getPopularMovies(authorization: String, pageNumber: Int) =
        RetrofitInstance.api.getMovies(authorization, pageNumber)

    suspend fun searchMovies(authorization: String, query: String, pageNumber: Int) =
        RetrofitInstance.api.searchMovie(authorization, query, pageNumber)

    suspend fun addFavourite(movie: Movie) = db.getMovieDao().addFavourite(movie)

    fun getFavouriteMovies() = db.getMovieDao().getAllFavourites()

    suspend fun deleteMovie(movie: Movie) = db.getMovieDao().deleteFavouriteMovie(movie)
}