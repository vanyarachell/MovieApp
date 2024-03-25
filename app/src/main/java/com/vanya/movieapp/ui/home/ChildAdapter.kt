package com.vanya.movieapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vanya.movieapp.R
import com.vanya.movieapp.databinding.ItemCategoryBinding
import com.vanya.movieapp.model.Genre

/**
 * Created by vanyarachell on Sun, 24 Mar 2024
 * vanyarachel05@gmail.com
 */
class ChildAdapter constructor(private val listGenre: List<Genre>) : RecyclerView.Adapter<ChildAdapter.ItemViewHolder>() {

    private val listItem = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemCategoryBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_category, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount() = listItem.size

    fun updateData(listIds: List<Int>) {
        listItem.clear()
        val listString = arrayListOf<String>()
        listIds.forEach { ids ->
            listGenre.forEach { genre ->
                genre.id?.let {
                    if (ids == it) {
                        genre.name?.let { name ->
                            listString.add(name)
                        }
                    }
                }
            }
        }

        listItem.addAll(listString)
        notifyItemRangeChanged(0, listItem.size)
    }

    inner class ItemViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.category = item
        }
    }
}