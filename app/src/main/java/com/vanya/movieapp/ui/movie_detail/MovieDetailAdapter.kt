package com.vanya.movieapp.ui.movie_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vanya.movieapp.R
import com.vanya.movieapp.databinding.ItemGenreSimpleBinding

class MovieDetailAdapter constructor(private val listGenre: List<String>) :
    RecyclerView.Adapter<MovieDetailAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemGenreSimpleBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_genre_simple, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(listGenre[position])
    }

    override fun getItemCount() = listGenre.size

    inner class ItemViewHolder(private val binding: ItemGenreSimpleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.category = item
        }
    }
}