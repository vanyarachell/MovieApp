package com.vanya.movieapp.ui.movie_detail

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.vanya.movieapp.databinding.FragmentMovieDetailBinding
import com.vanya.movieapp.ui.dashboard.DashboardViewModel

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val navigationArgs: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Toast.makeText(
            this@MovieDetailFragment.context,
            navigationArgs.movie.releaseDate,
            Toast.LENGTH_SHORT
        ).show()

        with(binding) {
            movie = navigationArgs.movie

            pb.apply {
                movie?.let {
                    max = 100
                    ObjectAnimator.ofInt(pb, "progress", it.percentageScoreWithoutSymbol())
                        .setDuration(2000)
                        .start()
                }
            }
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}