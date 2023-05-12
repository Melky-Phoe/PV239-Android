package cz.muni.packer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import cz.muni.packer.data.Item
import cz.muni.packer.data.loadImageFromFirebase
import cz.muni.packer.databinding.ItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemAdapter(
    private val onItemClick: (Item) -> Unit,
    private val onCountUpdate: (Item) -> Unit,
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
        holder.bind(getItem(position), onItemClick, onCountUpdate)
    }
}

class ItemViewHolder(
    private val binding: ItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var _item: Item
    private var _currentCount: Int? = 0
        set(value) {
            field = value
            _item.currentCount = value
            updateCountText()
            if (value == _item.totalCount) {
                binding.checkBox.isChecked = true
            }
        }
        get() {
            return _item.currentCount
        }

    private fun updateCountText() {
        binding.itemCounterText.text = "${_currentCount}/${_item.totalCount}"
    }
    
    fun bind(item: Item, onItemClick: (Item) -> Unit, onCountUpdate: (Item) -> Unit) {
        _item = item
        binding.itemName.text = item.name
        updateCountText()

        item.picture?.let { bindPicture(it) }
        binding.plusButton.setOnClickListener {
            if (item.currentCount!! < item.totalCount!!) {
                _currentCount = _currentCount!! + 1
                onCountUpdate(item)
            }
        }

        binding.minusButton.setOnClickListener {
            if (item.currentCount == item.totalCount) {
                binding.checkBox.isEnabled = true
                binding.checkBox.isChecked = false
            }
            if (item.currentCount!! > 0) {
                _currentCount = _currentCount!! - 1
                onCountUpdate(item)
            }
        }

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                _currentCount = item.totalCount
                onCountUpdate(item)
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

    private fun bindPicture(pictureUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val picture = loadImageFromFirebase(pictureUrl)
            picture?.let {
                CoroutineScope(Dispatchers.Main).launch {
                    binding.imageItem.setImageBitmap(it)
                }
            }
        }
    }
}


class ItemDiffUtil : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem == newItem

}