package com.vanya.movieapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vanya.movieapp.R
import com.vanya.movieapp.databinding.FragmentHomeBinding
import com.vanya.movieapp.model.GenresItem
import com.vanya.movieapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    companion object {
        private const val TAG = "FIRST FRAGMENT => "
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private lateinit var mAdapter: HomeAdapter
//    private lateinit var mViewModel: HomeViewModel

    private val mViewModel by viewModels<HomeViewModel>()


    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        mViewModel.getGenreList()
        binding.apply {

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.e("my onQueryTextSubmit", "===== ${query}")

                    query?.let {
//                        doSearch(it)
                        // todo DO SEARCH
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.e("my onQueryTextChange", "===== ${newText}")
                    return false
                }

            })

        }


        mViewModel.genreList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    hideErrorMessage()
                    mViewModel.getPopularMovies()
                }

                is Resource.Error -> {
                    hideProgressBar()
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }

        mViewModel.popularMovies.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {

                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*private fun getGenres() {
        val repoGenres = RetrofitBuilder.service.getGenres(TOKEN)
        repoGenres?.enqueue(object : Callback<GenreResponse> {
            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
                response.body()?.genres?.let { listGenres ->
                    Log.e(TAG, "$listGenres")

                    for (genre in listGenres) {
                        Log.e(TAG, "${genre.id}")
                    }

                    if (response.isSuccessful) {
                        setupAdapter(listGenres)
                        getMovies()
                    }
                }
            }

            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                Log.e(TAG, "${t.message}")
            }
        })
    }*/

    /* private fun getMovies() {
         val repoMovies = RetrofitBuilder.service.getMovies(TOKEN, 1)
         repoMovies?.enqueue(object : Callback<MovieResponse> {
             override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {


                 response.body()?.results?.let { listMovies ->
                     Log.e(TAG, "$listMovies")

                     for (game in listMovies) {
                         Log.e(TAG, "${game.id}")
                     }
                     mAdapter.updateData(listMovies)
                 }
             }

             override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                 Log.e(TAG, "${t.message}")
             }
         })
     }*/

    /*  private fun doSearch(query: String) {
          val repoMovies = RetrofitBuilder.service.searchMovie(TOKEN, query)
          repoMovies?.enqueue(object : Callback<MovieResponse> {
              override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {


                  response.body()?.results?.let { listSearch ->
                      Log.e(TAG, "$listSearch")

                      for (movie in listSearch) {
                          Log.e(TAG, "${movie.id}")
                      }

                      Log.e(TAG, "==== UKURAN ${listSearch.size}")

                      if (listSearch.isNotEmpty()) {
                          mAdapter.updateData(listSearch)
                      } else {
                          Toast.makeText(
                              this@HomeFragment.context,
                              "Movie Not Found",
                              Toast.LENGTH_SHORT
                          ).show()
                      }
                  }
              }

              override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                  Log.e(TAG, "${t.message}")
              }
          })
      }*/


    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideErrorMessage() {
        binding.itemErrorMessage.cvError.visibility = View.INVISIBLE
        isError = false
    }

    private fun showErrorMessage(message: String) {
        binding.itemErrorMessage.cvError.visibility = View.VISIBLE
        binding.itemErrorMessage.tvErrorMessage.text = message
        isError = true
    }

    private fun setupAdapter(list: List<GenresItem>) {
        with(binding) {
            mAdapter = HomeAdapter(
                listGenre = list,
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
            rvMovies.adapter = mAdapter
        }
    }
}