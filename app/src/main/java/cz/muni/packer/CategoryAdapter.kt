package cz.muni.packer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.muni.packer.data.Category
import cz.muni.packer.data.Item
import cz.muni.packer.databinding.CategoryBinding
import cz.muni.packer.repository.ItemRepository

class CategoryAdapter(
    private val categories: List<Category>,
    private val appNavigator: AppNavigator,
    private val itemRepository: ItemRepository
) : RecyclerView.Adapter<CategoryViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding, appNavigator, itemRepository)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

}

class CategoryViewHolder(
    private val binding: CategoryBinding,
    private val appNavigator: AppNavigator,
    private val itemRepository: ItemRepository
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var adapter: ItemAdapter

    fun bind(category: Category, context: Context) {
        adapter = ItemAdapter(
            onItemClick = { item ->
                showItemDetails(item)
            },
            onCountUpdate = { item ->
                updateItemCount(item)
            }
        )
        binding.apply {
            tvCategoryName.text = category.name
            rvCategoryItems.adapter = adapter
            rvCategoryItems.layoutManager = LinearLayoutManager(context)
            adapter.submitList(category.items)
        }
    }

    private fun showItemDetails(item: Item) {
        appNavigator.navigateToItemDetails(item)
    }

    private fun updateItemCount(item: Item) {
        itemRepository.updateCount(item.id, item.currentCount)
    }
}


