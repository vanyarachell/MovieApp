package com.vanya.movieapp.ui.favorites

import androidx.lifecycle.ViewModel
import com.vanya.movieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {
    fun getSavedMovies() = movieRepository.getFavouriteMovies()
}