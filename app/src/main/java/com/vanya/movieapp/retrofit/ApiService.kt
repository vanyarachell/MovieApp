package com.vanya.movieapp.retrofit

import com.vanya.movieapp.model.GenreResponse
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
    @GET("movie/popular")
    fun getMovies(
        @Header("Authorization") authorization: String,
        @Query("page") currentPage: Int,
    ): Call<MovieResponse>?


   /* val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://api.themoviedb.org/3/genre/movie/list?language=en")
        .get()
        .addHeader("accept", "application/json")
        .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4YmMzODExNGYxN2E4ZTMwMDJhNWUxNTFiMWFjMmJkYSIsInN1YiI6IjU3MjI0ZGVlYzNhMzY4MmQxZTAwMDA3MyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.3eFEU9Ajy3WJAlKDXTV3hVNEc7Al4QJMjRIcx9N9HUo")
        .build()

    val response = client.newCall(request).execute()*/
    @GET("genre/movie/list")
    fun getGenres(
        @Header("Authorization") authorization: String
    ): Call<GenreResponse>?


   /* val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://api.themoviedb.org/3/search/movie?query=capten&include_adult=false&language=en-US&page=1")
        .get()
        .addHeader("accept", "application/json")
        .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4YmMzODExNGYxN2E4ZTMwMDJhNWUxNTFiMWFjMmJkYSIsInN1YiI6IjU3MjI0ZGVlYzNhMzY4MmQxZTAwMDA3MyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.3eFEU9Ajy3WJAlKDXTV3hVNEc7Al4QJMjRIcx9N9HUo")
        .build()

    val response = client.newCall(request).execute()*/

    @GET("search/movie")
    fun searchMovie(
        @Header("Authorization") authorization: String,
        @Query("query") query: String,
    ): Call<MovieResponse>?
}