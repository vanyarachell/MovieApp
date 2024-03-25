package com.vanya.movieapp.ui.movie_detail

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vanya.movieapp.R
import com.vanya.movieapp.databinding.FragmentMovieDetailBinding
import com.vanya.movieapp.ui.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    //    private var _binding: FragmentMovieDetailBinding? = null

    private var binding: FragmentMovieDetailBinding? = null

    private val navigationArgs: MovieDetailFragmentArgs by navArgs()

    private val mViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        binding?.apply {
            lifecycleOwner = this@MovieDetailFragment.viewLifecycleOwner
        }

        binding?.apply {

            movie = navigationArgs.movie

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
        binding?.btnFav?.setOnClickListener {
            findNavController().navigate(MovieDetailFragmentDirections.actionNavigationDetailToFavorite())
        }
        binding?.ibFavoriteDetail?.setOnClickListener {
            mViewModel.saveMovie(navigationArgs.movie)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}