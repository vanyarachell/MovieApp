package com.vanya.movieapp.repository

import com.vanya.movieapp.api.RetrofitInstance

class MovieRepository {
    suspend fun getPopularMovies(authorization: String, pageNumber: Int) =
        RetrofitInstance.api.getMovies(authorization, pageNumber)

    suspend fun getGenres(authorization: String) =
        RetrofitInstance.api.getGenres(authorization)
}