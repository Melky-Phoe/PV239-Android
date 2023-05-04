package cz.muni.packer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import cz.muni.packer.data.Item
import cz.muni.packer.data.byteArrayToBitmap
import cz.muni.packer.databinding.ItemBinding

class ItemAdapter(
    private val onItemClick: (Item) -> Unit //, private val updateListener: (Item) -> Unit,
) : ListAdapter<Item, ItemViewHolder>(ItemDiffUtil()) {
    interface UpdateListener {
        fun onCurrentCountUpdate(item: Item)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            //, updateListener
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
        //holder.bind(getItem(position), updateListener)
    }
}

class ItemViewHolder(
    private val binding: ItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var _item: Item

    private fun updateCountText() {
        binding.itemCounterText.text = "${_item.currentCount}/${_item.totalCount}"
    }

    fun bind(item: Item, onItemClick: (Item) -> Unit) {
        _item = item
        binding.itemName.text = item.name
        updateCountText()

        item.picture?.let { bindPicture(it) }
        binding.plusButton.setOnClickListener {
            if (_item.currentCount < _item.totalCount) {
                _item.currentCount++
                updateCountText()
                if (_item.currentCount == _item.totalCount) {
                    binding.checkBox.isChecked = true
                }
            }
        }

        binding.minusButton.setOnClickListener {
            if (_item.currentCount == _item.totalCount) {
                binding.checkBox.isEnabled = true
                binding.checkBox.isChecked = false
            }
            if (_item.currentCount > 0) {
                _item.currentCount--
                updateCountText()
            }
        }

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                _item.currentCount = _item.totalCount
                updateCountText()
                binding.checkBox.isEnabled = false // Disable checkbox
            }
        }

        binding.root.setOnLongClickListener {
            onItemClick(item)
            true
        }
    }

    private fun bindPicture(pictureBytes: ByteArray) {
        val picture = byteArrayToBitmap(pictureBytes)
        binding.imageItem.setImageBitmap(picture)
    }
}


class ItemDiffUtil : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem == newItem

}