package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemCategoryBinding

class ItemCategoryAdapter(
    private val categories: MutableList<ItemCategory>
) : RecyclerView.Adapter<ItemCategoryAdapter.ItemCategoryViewHolder>() {

    class ItemCategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: ItemCategory, context: Context) {
            val itemAdapter = ItemAdapter(category.items)
            binding.apply {
                tvCategoryName.text = category.name
                rvItems.adapter = itemAdapter
                rvItems.layoutManager = LinearLayoutManager(context)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemCategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ItemCategoryViewHolder,
        position: Int
    ) {
        holder.bind(categories[position], holder.itemView.context)

    }

    override fun getItemCount(): Int {
        return categories.size
    }

}