package com.vanya.movieapp.ui.movie_rated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanya.movieapp.model.Movie
import com.vanya.movieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieRatedViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    var isFavorite: Boolean = false

    fun saveMovie(movie: Movie) = viewModelScope.launch {
        movieRepository.addFavourite(movie)
    }

    fun deleteMovie(movie: Movie) = viewModelScope.launch {
        movieRepository.deleteMovie(movie)
    }

    fun getSavedMovies() = movieRepository.getFavouriteMovies()
}