package com.vanya.movieapp.ui.home

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanya.movieapp.model.MovieResponse
import com.vanya.movieapp.repository.MovieRepository
import com.vanya.movieapp.util.Constants.Companion.TOKEN
import com.vanya.movieapp.util.RequestType
import com.vanya.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val connectivityManager: ConnectivityManager
) : ViewModel() {

    var moviePage = 1
    var searchPage = 1

    val popularMovies: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    private var popularMovieResponse: MovieResponse? = null

    val searchedMovies: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    private var searchedMovieResponse: MovieResponse? = null

    var reqType = RequestType.DEFAULT

    fun getPopularMovies() = viewModelScope.launch {
        safePopularMoviesCall()
    }

    fun searchMovies(query: String) = viewModelScope.launch {
        searchMoviesCall(query)
    }

    private suspend fun safePopularMoviesCall() {
        popularMovies.postValue(Resource.Loading())
        reqType = RequestType.POPULAR_MOVIE
        try {
            if (hasInternetConnection()) {
                val response = movieRepository.getPopularMovies("Bearer $TOKEN", moviePage)
                popularMovies.postValue(handleMovieResponse(response))
            } else {
                popularMovies.postValue(
                    Resource.Error(
                        message = "No internet connection",
                    )
                )
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> popularMovies.postValue(
                    Resource.Error(
                        message = "Network Failure",
                    )
                )

                else -> popularMovies.postValue(
                    Resource.Error(
                        message = "Conversion Error",
                    )
                )
            }

        }
    }

    private suspend fun searchMoviesCall(query: String) {
        reqType = RequestType.SEARCH_MOVIE
        try {
            if (hasInternetConnection()) {
                searchedMovies.postValue(Resource.Loading())
                val response = movieRepository.searchMovies("Bearer $TOKEN", query, searchPage)
                searchedMovies.postValue(handleSearchResponse(response))
            } else {
                searchedMovies.postValue(
                    Resource.Error(
                        message = "No internet connection",
                    )
                )
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> searchedMovies.postValue(
                    Resource.Error(
                        message = "Network Failure",
                    )
                )

                else -> searchedMovies.postValue(
                    Resource.Error(
                        message = "Conversion Error",
                    )
                )
            }
        }
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

    private fun hasInternetConnection(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}