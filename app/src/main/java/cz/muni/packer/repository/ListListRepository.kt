package cz.muni.packer.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.muni.packer.data.Item
import cz.muni.packer.PackerList
import cz.muni.packer.data.Categories
import cz.muni.packer.database.ItemDao
import cz.muni.packer.database.ItemDatabase
import kotlin.collections.List

class ListListRepository (
    context: Context,
    private val dao: ItemDao = ItemDatabase.create(context).ItemDao()
    ) {
    private val _lists = MutableLiveData<List<PackerList>>()
    val lists: LiveData<List<PackerList>> = _lists


    fun loadItems() {
        val exampleItems = mutableListOf<Item>(
            Item(0, "Example 1", Categories.CLOTHING, null, 0, 1),
            Item(1, "Example 2", Categories.FOOD, null, 0, 3),
            Item(2, "Example 3", Categories.OTHER, null, 0, 2),
            Item(3, "Example 4", Categories.CLOTHING, null, 2, 5),
            Item(4, "Example 5", Categories.CLOTHING, null, 0, 1)
        )
        val exampleLists = mutableListOf<PackerList>(
            PackerList("List 1", exampleItems),
            PackerList("List 2", exampleItems),
            PackerList("List 3", mutableListOf<Item>())
        )
        _lists.value = exampleLists
    }

    fun updateItemCount(item: Item) {
        dao.updateCount(item.currentCount, item.id)
    }
}