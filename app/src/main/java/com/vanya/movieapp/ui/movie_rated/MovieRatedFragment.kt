package com.vanya.movieapp.ui.movie_rated

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vanya.movieapp.R
import com.vanya.movieapp.databinding.FragmentMovieRatedBinding
import com.vanya.movieapp.ui.movie_detail.RatingDialogFragment
import com.vanya.movieapp.util.Constants
import com.vanya.movieapp.util.Constants.Companion.REQUEST_GET_RATE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieRatedFragment : Fragment(R.layout.fragment_movie_rated) {

    private var binding: FragmentMovieRatedBinding? = null

    private val mViewModel by viewModels<MovieRatedViewModel>()

    private val navigationArgs: MovieRatedFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieRatedBinding.inflate(inflater, container, false)

        binding?.apply {
            lifecycleOwner = this@MovieRatedFragment.viewLifecycleOwner
            movie = navigationArgs.movie

            btnBackRated.setOnClickListener {
                findNavController().navigate(MovieRatedFragmentDirections.actionNavigationRatedToHome())
            }

            ibRatingRated.setOnClickListener {
                if (mViewModel.isFavorite) mViewModel.deleteMovie(navigationArgs.movie)
                else mViewModel.saveMovie(navigationArgs.movie)
                mViewModel.isFavorite = !mViewModel.isFavorite
                navigationArgs.movie.apply {
                    isFavorite = !isFavorite
                }
                setupBtnFavorite(this)
            }

            btnResetRate.setOnClickListener {
                childFragmentManager.let {
                    RatingDialogFragment.newInstance("TITLE", "DETAIL").show(
                        it, "TAGH"
                    )
                }
            }

            btnFavNav.setOnClickListener {
                findNavController().navigate(
                    MovieRatedFragmentDirections.actionNavigationRatedToFavorite(
                        navigationArgs.movie
                    )
                )
            }

            childFragmentManager.setFragmentResultListener(
                REQUEST_GET_RATE,
                this@MovieRatedFragment.viewLifecycleOwner
            ) { _, bundle ->
                val result = bundle.getString(Constants.ITEM_RATE)

                val updatedMovie = navigationArgs.movie.apply {
                    personalRating = result?.toInt() ?: 0
                }
                mViewModel.saveMovie(updatedMovie)
                tvRatedScore.text = result
            }

            checkFavourite(this)
        }
        return binding?.root
    }

    private fun checkFavourite(binding: FragmentMovieRatedBinding) {
        mViewModel.getSavedMovies().observe(viewLifecycleOwner) { movies ->
            Log.d("favorite movie", movies.toString())
            movies.let {
                it.forEach { favorite ->
                    if (favorite.title == navigationArgs.movie.title) {
                        mViewModel.isFavorite = favorite.isFavorite
                    }
                }
            }
            setupBtnFavorite(binding)
        }
    }

    private fun setupBtnFavorite(binding: FragmentMovieRatedBinding) = with(binding) {
        if (mViewModel.isFavorite) ibRatingRated.setBackgroundResource(R.drawable.ic_favorite_on)
        else ibRatingRated.setBackgroundResource(R.drawable.ic_favorite_off)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}