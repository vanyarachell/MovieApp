package com.vanya.movieapp.ui.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vanya.movieapp.R
import com.vanya.movieapp.databinding.ItemMovieBinding
import com.vanya.movieapp.model.GenresItem
import com.vanya.movieapp.model.Movie

/**
 * Created by vanyarachell on Sun, 24 Mar 2024
 * vanyarachel05@gmail.com
 */
class HomeAdapter constructor(val listGenre: List<GenresItem>) :
    RecyclerView.Adapter<HomeAdapter.ItemViewHolder>() {

    private val listItem = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemMovieBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_movie, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount() = listItem.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(list: List<Movie>) {
        listItem.clear()
        listItem.addAll(list)
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.movie = item
            Log.e("GENRE ", "==== SIZE GENRE ${listGenre.size}")
            val mChildAdapter = ChildAdapter(listGenre)
            binding.childAdapter = mChildAdapter
            item.genreIds?.let {
                mChildAdapter.updateData(it)
            }
        }
    }
}