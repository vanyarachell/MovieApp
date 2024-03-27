package com.vanya.movieapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanya.movieapp.model.GenreResponse
import com.vanya.movieapp.model.MovieResponse
import com.vanya.movieapp.repository.MovieRepository
import com.vanya.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    var moviePage = 1
    var searchPage = 1

    val popularMovies: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    private var popularMovieResponse: MovieResponse? = null

    val searchedMovies: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    private var searchedMovieResponse: MovieResponse? = null

    val TOKEN =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4YmMzODExNGYxN2E4ZTMwMDJhNWUxNTFiMWFjMmJkYSIsInN1YiI6IjU3MjI0ZGVlYzNhMzY4MmQxZTAwMDA3MyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.3eFEU9Ajy3WJAlKDXTV3hVNEc7Al4QJMjRIcx9N9HUo"

    fun getPopularMovies() = viewModelScope.launch {
        safePopularMoviesCall()
    }

    fun searchMovies(query: String) = viewModelScope.launch {
        searchMoviesCall(query)
    }

    private suspend fun safePopularMoviesCall() {
        popularMovies.postValue(Resource.Loading())
        val response = movieRepository.getPopularMovies("Bearer $TOKEN", moviePage)
        popularMovies.postValue(handleMovieResponse(response))
    }

    private suspend fun searchMoviesCall(query: String) {
        searchedMovies.postValue(Resource.Loading())
        val response = movieRepository.searchMovies("Bearer $TOKEN", query, searchPage)
        searchedMovies.postValue(handleSearchResponse(response))
    }

    private fun handleMovieResponse(response: Response<MovieResponse>): Resource<MovieResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                moviePage++
                if (popularMovieResponse == null) {
                    popularMovieResponse = resultResponse
                } else {
                    val oldMovies = popularMovieResponse?.results
                    val newArticles = resultResponse.results
                    newArticles?.let {
                        oldMovies?.addAll(it)
                    }
                }
                return Resource.Success(popularMovieResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchResponse(response: Response<MovieResponse>): Resource<MovieResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchPage++
                if (searchedMovieResponse == null) {
                    searchedMovieResponse = resultResponse
                } else {
                    val oldMovies = searchedMovieResponse?.results
                    val newArticles = resultResponse.results
                    newArticles?.let {
                        oldMovies?.addAll(it)
                    }
                }
                return Resource.Success(searchedMovieResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}