package com.vanya.movieapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vanya.movieapp.databinding.FragmentHomeBinding
import com.vanya.movieapp.model.GenreResponse
import com.vanya.movieapp.model.GenresItem
import com.vanya.movieapp.model.MovieResponse
import com.vanya.movieapp.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    companion object {
        private const val TAG = "FIRST FRAGMENT => "
        private const val TOKEN =
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4YmMzODExNGYxN2E4ZTMwMDJhNWUxNTFiMWFjMmJkYSIsInN1YiI6IjU3MjI0ZGVlYzNhMzY4MmQxZTAwMDA3MyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.3eFEU9Ajy3WJAlKDXTV3hVNEc7Al4QJMjRIcx9N9HUo"
    }

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mAdapter: HomeAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.e("my onQueryTextSubmit", "===== ${query}")

                    query?.let {
                        doSearch(it)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.e("my onQueryTextChange", "===== ${newText}")
                    return false
                }

            })
        }

        getGenres()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getGenres() {
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
    }

    private fun getMovies() {
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
    }

    private fun doSearch(query: String) {
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
    }

    private fun setupAdapter(list: List<GenresItem>) {
        with(binding) {
            mAdapter = HomeAdapter(
                listGenre = list,
                onClick = {
                    it.id?.let { id ->
                        Toast.makeText(this@HomeFragment.context, id.toString(), Toast.LENGTH_SHORT).show()
                    }
                })
            rv.adapter = mAdapter
        }
    }
}