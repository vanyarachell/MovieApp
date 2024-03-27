package com.vanya.movieapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vanya.movieapp.R
import com.vanya.movieapp.databinding.ItemCategoryBinding

/**
 * Created by vanyarachell on Sun, 24 Mar 2024
 * vanyarachel05@gmail.com
 */
class ChildAdapter constructor(private val listGenre: List<String>) :
    RecyclerView.Adapter<ChildAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemCategoryBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_category, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(listGenre[position])
    }

    override fun getItemCount() = listGenre.size

    inner class ItemViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.category = item
        }
    }
}