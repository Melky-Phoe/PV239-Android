package com.example.myapplication

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
        exampleItems.add(Item("Example 1"))
        exampleItems.add(Item("Example 2"))

        itemAdapter = ItemAdapter(exampleItems)
        binding.rvItems.adapter = itemAdapter
        binding.rvItems.layoutManager = LinearLayoutManager(this)


    }
}