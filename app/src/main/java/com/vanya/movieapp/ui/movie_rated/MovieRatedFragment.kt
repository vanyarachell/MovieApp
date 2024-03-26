package com.vanya.movieapp.ui.movie_rated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vanya.movieapp.R
import com.vanya.movieapp.databinding.FragmentMovieRatedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieRatedFragment : Fragment(R.layout.fragment_movie_rated) {

    private var binding: FragmentMovieRatedBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieRatedBinding.inflate(inflater, container, false)

        binding?.apply {
            lifecycleOwner = this@MovieRatedFragment.viewLifecycleOwner

        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}