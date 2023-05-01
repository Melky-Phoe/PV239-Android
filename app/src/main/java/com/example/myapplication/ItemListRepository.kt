package com.example.myapplication

import android.graphics.Picture
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ItemsRepository {
    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items

    fun loadItems() {
        val exampleItems = mutableListOf<Item>(
            Item("Example 1", "Category 1", Picture(), 0, 1),
            Item("Example 2", "Category 1", Picture(), 0, 3),
            Item("Example 3", "Category 2", Picture(), 0, 2),
            Item("Example 4", "Category 3", Picture(), 2, 5),
            Item("Example 5", "Category 2", Picture(), 0, 1)
        )
        _items.value = exampleItems
    }
}