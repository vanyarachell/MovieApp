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

    val popularMovies: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    var moviePage = 1
    private var popularMovieResponse: MovieResponse? = null

    val genreList: MutableLiveData<Resource<GenreResponse>> = MutableLiveData()

    val TOKEN =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4YmMzODExNGYxN2E4ZTMwMDJhNWUxNTFiMWFjMmJkYSIsInN1YiI6IjU3MjI0ZGVlYzNhMzY4MmQxZTAwMDA3MyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.3eFEU9Ajy3WJAlKDXTV3hVNEc7Al4QJMjRIcx9N9HUo"

    fun getPopularMovies() = viewModelScope.launch {
        safePopularMoviesCall()
    }

    fun getGenreList() = viewModelScope.launch {
        safeGenreListCall()
    }

    private suspend fun safePopularMoviesCall() {
        popularMovies.postValue(Resource.Loading())
        val response = movieRepository.getPopularMovies("Bearer $TOKEN", moviePage)
        popularMovies.postValue(handlePopularMovieResponse(response))
    }

    private suspend fun safeGenreListCall() {
        genreList.postValue(Resource.Loading())
        val response = movieRepository.getGenres("Bearer $TOKEN")
        genreList.postValue(handleGenresResponse(response))
    }

    private fun handlePopularMovieResponse(response: Response<MovieResponse>): Resource<MovieResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                moviePage++
                if (popularMovieResponse == null) {
                    popularMovieResponse = resultResponse
                } else {
                    val oldMovies = popularMovieResponse?.results
                    val newArticles = resultResponse.results
                    newArticles?.let {
                        oldMovies?.plus(it)
                    }
                }
                return Resource.Success(popularMovieResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleGenresResponse(response: Response<GenreResponse>): Resource<GenreResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.message())
    }
}