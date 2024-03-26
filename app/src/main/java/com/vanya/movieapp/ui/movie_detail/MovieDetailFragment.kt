package com.vanya.movieapp.ui.movie_detail

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vanya.movieapp.R
import com.vanya.movieapp.databinding.FragmentMovieDetailBinding
import com.vanya.movieapp.model.Movie
import com.vanya.movieapp.ui.HomeViewModel
import com.vanya.movieapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    //    private var _binding: FragmentMovieDetailBinding? = null

    private var binding: FragmentMovieDetailBinding? = null

    private val navigationArgs: MovieDetailFragmentArgs by navArgs()

    private val mViewModel by viewModels<HomeViewModel>()

    private lateinit var mAdapter: MovieDetailAdapter

    private lateinit var dialog: DialogFragment

    private var mMovie = Movie()

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

                Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                // Do something with the result.

                val updatedMovie = mMovie.apply {
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
        }

        binding?.apply {
            dialog = DialogFragment()
            dialog.dialog?.setContentView(R.layout.fragment_dialog_rating)
            dialog.isCancelable = false


            movie = navigationArgs.movie
            mMovie = navigationArgs.movie

            Toast.makeText(
                this@MovieDetailFragment.context,
                navigationArgs.movie.releaseDate,
                Toast.LENGTH_SHORT
            ).show()

            // add animation to progressHorizontal
            pb.apply {
                movie?.let {
                    max = 100
                    ObjectAnimator.ofInt(pb, "progress", it.percentageScoreWithoutSymbol())
                        .setDuration(2000)
                        .start()
                }
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

                mViewModel.saveMovie(navigationArgs.movie)
                Toast.makeText(context, "Saved to Favourite", Toast.LENGTH_LONG)
                    .show()
            }

            btnRating.setOnClickListener {
                childFragmentManager.let {
                    RatingDialogFragment.newInstance("TITLE", "DETAIL").show(
                        it, "TAGH"
                    )
                }
            }
        }

        mAdapter = MovieDetailAdapter(navigationArgs.movie.getListGenre())
        binding?.rvGenre?.adapter = mAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}