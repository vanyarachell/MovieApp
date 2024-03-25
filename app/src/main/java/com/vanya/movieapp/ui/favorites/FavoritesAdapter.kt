package com.vanya.movieapp.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vanya.movieapp.R
import com.vanya.movieapp.databinding.ItemFavoriteBinding
import com.vanya.movieapp.model.Movie

/**
 * Created by vanyarachell on Sun, 24 Mar 2024
 * vanyarachel05@gmail.com
 */
class FavoritesAdapter(
    val onClick: (Movie) -> Unit
) :
    RecyclerView.Adapter<FavoritesAdapter.ItemViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemFavoriteBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_favorite, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    override fun getItemCount() = differ.currentList.size

    inner class ItemViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.movie = item
            binding.ivMovieFavorite.setOnClickListener {
                onClick(item)
            }
        }
    }
}