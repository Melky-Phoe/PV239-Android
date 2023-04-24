package com.example.myapplication

import android.graphics.Picture
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityItemListBinding

class ItemListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemListBinding
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var exampleItems = mutableListOf<Item>()
        exampleItems.add(Item("Example 1", "Category 1", Picture(), 0, 5))
        exampleItems.add(Item("Example 2", "Category 1", Picture(), 3, 4))
        exampleItems.add(Item("Example 3", "Category 2", Picture(), 1, 1))

        itemAdapter = ItemAdapter(exampleItems)
        binding.rvItems.adapter = itemAdapter
        binding.rvItems.layoutManager = LinearLayoutManager(this)


    }
}