package com.example.myapplication

import android.graphics.Picture
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityItemListBinding

class ItemListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemListBinding
    private lateinit var itemCategoryAdapter: ItemCategoryAdapter
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var exampleItems1 = mutableListOf<Item>()
        exampleItems1.add(Item("Example 1", "Category 1", Picture(), 0, 5))
        exampleItems1.add(Item("Example 2", "Category 1", Picture(), 3, 4))
        var exampleItems2 = mutableListOf<Item>()
        exampleItems2.add(Item("Example 3", "Category 2", Picture(), 1, 1))

        var exampleCategories = mutableListOf<ItemCategory>()
        exampleCategories.add(ItemCategory("Category 1", exampleItems1))
        exampleCategories.add(ItemCategory("Category 2", exampleItems2))

        itemCategoryAdapter = ItemCategoryAdapter(exampleCategories)
        binding.rvCategories.adapter = itemCategoryAdapter
        binding.rvCategories.layoutManager = LinearLayoutManager(this)
    }


}