package com.vanya.movieapp.ui.movie_rated

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vanya.movieapp.R
import com.vanya.movieapp.databinding.FragmentMovieRatedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieRatedFragment : Fragment(R.layout.fragment_movie_rated) {

    private var binding: FragmentMovieRatedBinding? = null

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
            Log.e("=== AB", navigationArgs.movie.fullBackdropUrl())

            btnBackRated.setOnClickListener {
                findNavController().navigate(MovieRatedFragmentDirections.actionNavigationRatedToHome())
            }
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}