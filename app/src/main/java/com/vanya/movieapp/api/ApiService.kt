package com.vanya.movieapp.api

import com.vanya.movieapp.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Created by vanyarachell on Sun, 24 Mar 2024
 * vanyarachel05@gmail.com
 */
interface ApiService {
    @GET("movie/popular")
    suspend fun getMovies(
        @Header("Authorization") authorization: String,
        @Query("page") currentPage: Int,
    ): Response<MovieResponse>

    @GET("search/movie")
    suspend fun searchMovie(
        @Header("Authorization") authorization: String,
        @Query("query") query: String,
        @Query("page") currentPage: Int
    ): Response<MovieResponse>
}