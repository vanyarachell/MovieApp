package com.vanya.movieapp.repository

import com.vanya.movieapp.api.RetrofitInstance

class MovieRepository {
    suspend fun getPopularMovies(authoriztion: String, pageNumber: Int) =
        RetrofitInstance.api.getMovies(authoriztion, pageNumber)

    suspend fun getGenres(authoriztion: String) =
        RetrofitInstance.api.getGenres(authoriztion)
}