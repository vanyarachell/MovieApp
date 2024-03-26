package com.vanya.movieapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vanya.movieapp.R
import com.vanya.movieapp.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private var binding: FragmentFavoritesBinding? = null

    private val mViewModel by viewModels<FavoriteViewModel>()

    private val mAdapter by lazy {
        FavoritesAdapter(
            onClick = {
                it.id?.let { id ->
                    Toast.makeText(
                        this@FavoritesFragment.context,
                        id.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    /* findNavController().navigate(
                         HomeFragmentDirections.actionNavigationHomeToNavigationDetail(
                             it
                         )
                     )*/
                }
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
        mViewModel.getSavedMovies().observe(viewLifecycleOwner) { movies ->
            mAdapter.differ.submitList(movies)
        }

        binding?.apply {
            rvFavorites.adapter = mAdapter

            btnBackDetail.setOnClickListener {
                findNavController().popBackStack(R.id.navigation_detail, true)
            }

            btnSearchNav.setOnClickListener {
                findNavController().navigate(FavoritesFragmentDirections.actionNavigationFavoriteToHome())
            }
        }
    }
}