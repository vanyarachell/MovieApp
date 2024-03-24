package com.vanya.movieapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vanya.movieapp.R
import com.vanya.movieapp.databinding.ItemCategoryBinding
import com.vanya.movieapp.model.GenresItem

/**
 * Created by vanyarachell on Sun, 24 Mar 2024
 * vanyarachel05@gmail.com
 */
class ChildAdapter : RecyclerView.Adapter<ChildAdapter.ItemViewHolder>() {

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

    fun updateData(list: List<GenresItem>, listIds: List<Int>) {
        listItem.clear()
        val listString = arrayListOf<String>()

        for (i in list) {
            for (j in listIds) {
                if (i.id == j) {
                    i.name?.let {
                        listString.add(it)
                    }
                }
            }
        }

        listItem.addAll(listString)
        notifyItemRangeChanged(0, list.size)
    }

    inner class ItemViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.category = item
        }
    }
}