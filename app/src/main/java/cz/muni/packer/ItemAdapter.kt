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
    private val binding: ItemBinding //, private val updateListener: (Item) -> Unit
) : RecyclerView.ViewHolder(binding.root) {


    private lateinit var _item: Item
    private var _currentCount: Int = 0
        set(value) {
            field = value
            _item.currentCount = value
            // updateListener(_item)
            updateCountText()
            if (value == _item.totalCount) {
                binding.checkBox.isChecked = true
            }
        }

    private fun updateCountText() {
        binding.itemCounterText.text = "${_currentCount}/${_item.totalCount}"
    }
    
    fun bind(item: Item, onItemClick: (Item) -> Unit) {
        _item = item
        binding.itemName.text = item.name
        binding.itemCounterText.text = "${item.currentCount}/${item.totalCount}"

        item.picture?.let { bindPicture(it) }
        binding.plusButton.setOnClickListener {
            if (item.currentCount < item.totalCount) {
                _currentCount++
//                item.setCurrentCount(item.currentCount+1)
            }
        }

        binding.minusButton.setOnClickListener {
            if (item.currentCount == item.totalCount) {
                binding.checkBox.isEnabled = true
                binding.checkBox.isChecked = false
            }
            if (item.currentCount > 0) {
                _currentCount--
//                item.setCurrentCount(item.currentCount-1)
            }
        }

        binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                _currentCount = item.totalCount
//                item.setCurrentCount(item.totalCount)
                binding.checkBox.isEnabled = false // Disable checkbox
            }
        }
        if (item.currentCount == item.totalCount) {
            binding.checkBox.isChecked = true
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