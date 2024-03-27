package com.vanya.movieapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vanya.movieapp.R
import com.vanya.movieapp.databinding.FragmentHomeBinding
import com.vanya.movieapp.model.Genre
import com.vanya.movieapp.util.Constants.Companion.QUERY_PAGE_SIZE
import com.vanya.movieapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var binding: FragmentHomeBinding? = null

    private val mViewModel by viewModels<HomeViewModel>()

    private val mAdapter by lazy {
        HomeAdapter(
            onClick = {
                it.id?.let { _ ->
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
    var searchedWord = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding?.apply {
            lifecycleOwner = this@HomeFragment.viewLifecycleOwner
            vm = mViewModel
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding?.apply {
            mViewModel.getPopularMovies()
            Log.d("getPopularMovies", "onViewCreated")

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        searchedWord = it
                        if (searchedWord.isNotEmpty()) mViewModel.searchMovies(searchedWord)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        searchedWord = it
                        if (searchedWord.isEmpty()) mViewModel.getPopularMovies()
                    }
                    return false
                }

            })
        }

        mViewModel.popularMovies.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    hideErrorMessage()
                    response.data?.let { movieResponse ->
                        mAdapter.differ.submitList(movieResponse.results?.toList())
                        val totalPages = movieResponse.totalResults / QUERY_PAGE_SIZE + 2
                        isLastPage = mViewModel.moviePage == totalPages
                        if (isLastPage) {
                            binding?.rvMovies?.setPadding(0, 0, 0, 0)
                        }
                        binding?.tvTitleHome?.text = getString(R.string.popular_right_now)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG)
                            .show()
                        showErrorMessage(message)
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }

        mViewModel.searchedMovies.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    hideErrorMessage()
                    response.data?.let { movieResponse ->
                        mAdapter.differ.submitList(movieResponse.results?.toList())
                        val totalPages = movieResponse.totalResults / QUERY_PAGE_SIZE + 2
                        isLastPage = mViewModel.searchPage == totalPages
                        if (isLastPage) {
                            binding?.rvMovies?.setPadding(0, 0, 0, 0)
                        }
                        binding?.tvTitleHome?.text = getString(R.string.your_result)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG)
                            .show()
                        showErrorMessage(message)
                    }
                }

                is Resource.Loading -> {
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

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNoErrors = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate =
                isNoErrors && isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                        isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                if (searchedWord.isEmpty()) mViewModel.getPopularMovies()
                else mViewModel.searchMovies(searchedWord)
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun setupRecyclerView() {
        binding?.rvMovies?.apply {
            adapter = mAdapter
            addOnScrollListener(scrollListener)
        }
    }
}