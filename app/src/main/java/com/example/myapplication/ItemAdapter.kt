package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.databinding.ItemBinding

class ItemAdapter(
    private val onItemClick: (Item) -> Unit,
) : ListAdapter<Item, ItemViewHolder>(ItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClick)

}

class ItemViewHolder(
    private val binding: ItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Item, onItemClick: (Item) -> Unit) {
        binding.itemName.text = item.name

        binding.root.setOnClickListener {
            onItemClick(item)
        }
    }
}

class ItemDiffUtil : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem == newItem

}