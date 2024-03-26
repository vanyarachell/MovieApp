package com.vanya.movieapp.ui.movie_detail

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vanya.movieapp.R
import com.vanya.movieapp.databinding.FragmentMovieDetailBinding
import com.vanya.movieapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    //    private var _binding: FragmentMovieDetailBinding? = null

    private var binding: FragmentMovieDetailBinding? = null

    private val navigationArgs: MovieDetailFragmentArgs by navArgs()

    private val mViewModel by viewModels<MovieDetailViewModel>()

    private lateinit var mAdapter: MovieDetailAdapter

    private lateinit var dialog: DialogFragment

    private var isFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        binding?.apply {
            lifecycleOwner = this@MovieDetailFragment.viewLifecycleOwner

            childFragmentManager.setFragmentResultListener(
                Constants.REQUEST_GET_RATE,
                this@MovieDetailFragment.viewLifecycleOwner
            ) { _, bundle ->
                val result = bundle.getString(Constants.ITEM_RATE)
                Log.d("favorite movie result", result.toString())

                val updatedMovie = navigationArgs.movie.apply {
                    personalRating = result?.toInt() ?: 0
                }
                mViewModel.saveMovie(updatedMovie)
                // go to new page
                findNavController().navigate(
                    MovieDetailFragmentDirections.actionNavigationDetailToRated(
                        updatedMovie
                    )
                )
            }

            dialog = DialogFragment()
            dialog.dialog?.setContentView(R.layout.fragment_dialog_rating)
            dialog.isCancelable = false
            movie = navigationArgs.movie

            // add animation to progressHorizontal
            pb.apply {
                movie?.let {
                    max = 100
                    ObjectAnimator.ofInt(pb, "progress", it.percentageScoreWithoutSymbol())
                        .setDuration(2000)
                        .start()
                }
            }

            btnBackDetail.setOnClickListener {
                findNavController().navigate(MovieDetailFragmentDirections.actionNavigationDetailToHome())
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            btnFav.setOnClickListener {
                findNavController().navigate(MovieDetailFragmentDirections.actionNavigationDetailToFavorite())
            }

            ibFavoriteDetail.setOnClickListener {
                if (isFavorite) {
                    isFavorite = false
                    mViewModel.deleteMovie(navigationArgs.movie)
                } else {
                    isFavorite = true
                    mViewModel.saveMovie(navigationArgs.movie)
                }
                setupBtnFavorite(this)
            }

            btnRating.setOnClickListener {
                childFragmentManager.let {
                    RatingDialogFragment.newInstance("TITLE", "DETAIL").show(
                        it, "TAGH"
                    )
                }
            }

            mAdapter = MovieDetailAdapter(navigationArgs.movie.getListGenre())
            rvGenre.adapter = mAdapter

            /*checkFavourite()
            setupBtnFavorite(this)*/
        }
    }

    private fun checkFavourite() {
        Log.d("favorite movie", "checkFavourite")
        mViewModel.getSavedMovies().observe(viewLifecycleOwner) { movies ->
            Log.d("favorite movie", movies.toString())
            movies.forEach { favorite ->
                if (favorite.title == navigationArgs.movie.title) {
                    isFavorite = true
                }
            }
        }
    }

    private fun setupBtnFavorite(binding: FragmentMovieDetailBinding) = with(binding) {
        if (isFavorite) ibFavoriteDetail.setBackgroundResource(R.drawable.ic_favorite_on)
        else ibFavoriteDetail.setBackgroundResource(R.drawable.ic_favorite_off)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}