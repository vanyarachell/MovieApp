package com.vanya.movieapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vanya.movieapp.R
import com.vanya.movieapp.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private var binding: FragmentFavoritesBinding? = null

    private val mViewModel by viewModels<FavoriteViewModel>()

    private val navigationArgs: FavoritesFragmentArgs by navArgs()

    private val mAdapter by lazy {
        FavoritesAdapter(
            onClick = {
                mViewModel.deleteMovie(it)
                updateData()
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        binding?.apply {
            lifecycleOwner = this@FavoritesFragment.viewLifecycleOwner
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateData()

        binding?.apply {
            rvFavorites.adapter = mAdapter

            btnBackDetail.setOnClickListener {
                findNavController().navigate(
                    FavoritesFragmentDirections.actionNavigationFavoriteToDetail(
                        navigationArgs.movie
                    )
                )
            }

            btnSearchNav.setOnClickListener {
                findNavController().navigate(FavoritesFragmentDirections.actionNavigationFavoriteToHome())
            }
        }
    }

    private fun updateData() {
        mViewModel.getSavedMovies().observe(viewLifecycleOwner) { movies ->
            movies.let {
                mAdapter.differ.submitList(it.filter { movie -> movie.isFavorite })
            }
        }
    }
}