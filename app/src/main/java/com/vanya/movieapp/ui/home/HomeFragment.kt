package com.vanya.movieapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vanya.movieapp.databinding.FragmentHomeBinding
import com.vanya.movieapp.model.GenreResponse
import com.vanya.movieapp.model.GenresItem
import com.vanya.movieapp.model.Movie
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

    private val mAdapter: HomeAdapter by lazy {
        HomeAdapter()
    }

    private val movieList = arrayListOf<Movie>()

    private var mListGenres = listOf<GenresItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            with(rv) {
                adapter = mAdapter
            }
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
                    mListGenres = listGenres
                    getMovies()
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


                response.body()?.results?.let { listGame ->
                    Log.e(TAG, "${listGame}")

                    for (game in listGame) {
                        Log.e(TAG, "${game.id}")
                    }

                    movieList.addAll(listGame)
                    mAdapter.updateData(movieList, mListGenres)
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e(TAG, "${t.message}")
            }
        })
    }

}