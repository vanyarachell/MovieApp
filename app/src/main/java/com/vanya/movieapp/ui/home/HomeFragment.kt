package com.vanya.movieapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vanya.movieapp.R
import com.vanya.movieapp.databinding.FragmentHomeBinding
import com.vanya.movieapp.model.Genre
import com.vanya.movieapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    companion object {
        private const val TAG = "FIRST FRAGMENT => "
    }

    private var binding: FragmentHomeBinding? = null


    private val mViewModel by viewModels<HomeViewModel>()

    private var listGenres = listOf<Genre>()

    private val mAdapter by lazy {
        HomeAdapter(
            onClick = {
                it.id?.let { id ->
                    Toast.makeText(this@HomeFragment.context, id.toString(), Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(
                        HomeFragmentDirections.actionNavigationHomeToNavigationDetail(
                            it
                        )
                    )
                }
            })
    }

    private var isError = false
    private var isLoading = false
    private var isLastPage = false
    var isScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding?.apply {
            lifecycleOwner = this@HomeFragment.viewLifecycleOwner
            vm = mViewModel
            adapter = mAdapter
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.rvMovies?.adapter  = mAdapter
        binding?.apply {
            mViewModel.getGenreList()
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.e("my onQueryTextSubmit", "===== $query")

                    query?.let {
//                        doSearch(it)
                        // todo DO SEARCH
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.e("my onQueryTextChange", "===== $newText")
                    return false
                }

            })


        }

        mViewModel.genreList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    Log.e(" ==== GENRE LIST", "SUCCESS")
                    response.data?.genres?.toList()?.let {
                        listGenres = it
                        Log.e(" ==== GENRE LIST", listGenres.size.toString())
                    }
                    hideProgressBar()
                    hideErrorMessage()
                    mViewModel.getPopularMovies()
                }

                is Resource.Error -> {
                    Log.e(" ==== GENRE LIST", "ERROR")
                    hideProgressBar()
                }

                is Resource.Loading -> {
                    Log.e(" ==== GENRE LIST", "LOADING")
                    showProgressBar()
                }
            }
        }

        mViewModel.popularMovies.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    hideErrorMessage()
                    response.data?.let { movieResponse ->
                        movieResponse.results?.let {
                                mAdapter.updateData(it, listGenres)
                            Log.e(" ==== MOVIE LIST", it.size.toString())
                        }
                        /*       val totalPages = movieResponse.totalResults / QUERY_PAGE_SIZE + 2
                               isLastPage = mViewModel.moviePage == totalPages
                               if(isLastPage) {
                                   binding.rvMovies.setPadding(0, 0, 0, 0)
                               }*/
                    }
                }

                is Resource.Error -> {
                    Log.e(" ==== MOVIE LIST", "ERROR")

                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG)
                            .show()
                        showErrorMessage(message)
                    }
                }

                is Resource.Loading -> {
                    Log.e(" ==== MOVIE LIST", "LOADING")

                    showProgressBar()

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun hideProgressBar() {
        binding?.paginationProgressBar?.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding?.paginationProgressBar?.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideErrorMessage() {
        binding?.itemErrorMessage?.cvError?.visibility = View.INVISIBLE
        isError = false
    }

    private fun showErrorMessage(message: String) {
        binding?.itemErrorMessage?.cvError?.visibility = View.VISIBLE
        binding?.itemErrorMessage?.tvErrorMessage?.text = message
        isError = true
    }
}