package cz.muni.packer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.muni.packer.data.PackerList
import cz.muni.packer.databinding.ListBinding

class PackerListAdapter(
    private val onItemClick: (PackerList) -> Unit,
    private val onLongItemClick: (PackerList) -> Unit
) : ListAdapter<PackerList, PackerListViewHolder>(PackerListDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackerListViewHolder =
        PackerListViewHolder(
            ListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: PackerListViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClick, onLongItemClick)
}

class PackerListViewHolder(
    private val binding: ListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(packerList: PackerList, onItemClick: (PackerList) -> Unit, onLongItemClick: (PackerList) -> Unit) {
        binding.tvListTitle.text = packerList.name

        binding.root.setOnClickListener {
            onItemClick(packerList)
        }

        binding.root.setOnLongClickListener {
            onLongItemClick(packerList)
            true
        }
    }
}

class PackerListDiffUtil : DiffUtil.ItemCallback<PackerList>() {
    override fun areItemsTheSame(oldItem: PackerList, newItem: PackerList): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: PackerList, newItem: PackerList): Boolean =
        oldItem == newItem

}