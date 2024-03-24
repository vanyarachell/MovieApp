package com.vanya.movieapp.retrofit

import com.vanya.movieapp.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Created by vanyarachell on Sun, 24 Mar 2024
 * vanyarachel05@gmail.com
 */
interface ApiService {
    @GET("discover/movie")
    fun getGames(
        @Header("Authorization") authorization: String,
        @Query("page") currentPage: Int,
    ): Call<MovieResponse>?
}