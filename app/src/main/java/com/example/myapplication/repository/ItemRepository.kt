package com.example.myapplication.repository

import android.content.Context
import com.example.myapplication.data.Categories
import com.example.myapplication.data.Item
import com.example.myapplication.database.ItemDao
import com.example.myapplication.database.ItemDatabase
import com.example.myapplication.repository.mapper.toAppData
import com.example.myapplication.repository.mapper.toEntity
import java.util.*

class ItemRepository (
    context: Context,
    private val dao: ItemDao = ItemDatabase.create(context).ItemDao()
) {

    fun saveOrUpdate(name: String, category: Categories, picture: ByteArray?, currentCount: Int, totalCount: Int, id: Long? = null) {
        val item = Item(
            id = id ?: 0,
            name = name,
            category = category,
            picture = picture,
            currentCount = currentCount,
            totalCount = totalCount
        )

        dao.persist(item.toEntity())
    }

    fun getAllItems(): List<Item> =
        dao.selectAll()
            .map { it.toAppData() }

    fun saveAll(data: List<Item>) {
        val entities = data.map { it.toEntity() }
        dao.persist(entities)
    }
}