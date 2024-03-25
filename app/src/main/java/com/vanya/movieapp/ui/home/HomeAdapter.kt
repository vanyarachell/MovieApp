package com.vanya.movieapp.ui.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vanya.movieapp.R
import com.vanya.movieapp.databinding.ItemMovieBinding
import com.vanya.movieapp.model.Genre
import com.vanya.movieapp.model.Movie

/**
 * Created by vanyarachell on Sun, 24 Mar 2024
 * vanyarachel05@gmail.com
 */
class HomeAdapter(
    val onClick: (Movie) -> Unit
) :
    RecyclerView.Adapter<HomeAdapter.ItemViewHolder>() {

    private var listMovie = arrayListOf<Movie>()
    private var mListGenre = listOf<Genre>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemMovieBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_movie, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    override fun getItemCount() = listMovie.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(movieList: List<Movie>, listGenre: List<Genre>) {
        listMovie.clear()
        listMovie.addAll(movieList)
        mListGenre = listGenre
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.movie = item
            Log.e("GENRE ", "==== SIZE GENRE ${mListGenre.size}")
            binding.container.setOnClickListener {
                onClick(item)
            }

            val mChildAdapter = ChildAdapter(mListGenre)
            binding.childAdapter = mChildAdapter
            item.genreIds?.let {
                mChildAdapter.updateData(it)
            }
        }
    }
}