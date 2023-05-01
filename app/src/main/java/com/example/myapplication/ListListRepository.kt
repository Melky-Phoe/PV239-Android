package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.collections.List

class ListListRepository {
    private val _lists = MutableLiveData<List<PackerList>>()
    val lists: LiveData<List<PackerList>> = _lists

    fun loadItems() {
        val exampleItems = mutableListOf<Item>(
            Item("Example 1", "Category 1", "", 0, 1),
            Item("Example 2", "Category 1", "", 0, 3),
            Item("Example 3", "Category 2", "", 0, 2),
            Item("Example 4", "Category 3", "", 2, 5),
            Item("Example 5", "Category 2", "", 0, 1)
        )
        val exampleLists = mutableListOf<PackerList>(
            PackerList("List 1", exampleItems),
            PackerList("List 2", exampleItems),
            PackerList("List 3", mutableListOf<Item>())
        )
        _lists.value = exampleLists
    }
}