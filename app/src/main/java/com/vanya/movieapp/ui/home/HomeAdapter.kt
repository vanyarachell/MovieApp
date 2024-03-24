package com.vanya.movieapp.ui.home

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
class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ItemViewHolder>() {

    private val listItem = arrayListOf<Movie>()
    private var mListGenre = listOf<GenresItem>()

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

    fun updateData(list: ArrayList<Movie>, genreList: List<GenresItem>) {
        listItem.clear()
        listItem.addAll(list)
        notifyItemRangeChanged(0, list.size)
        mListGenre = genreList
    }

    inner class ItemViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.movie = item
            val mChildAdapter = ChildAdapter()
            binding.childAdapter = mChildAdapter
            item.genreIds?.let {
                mChildAdapter.updateData(mListGenre, it)
            }
        }
    }
}