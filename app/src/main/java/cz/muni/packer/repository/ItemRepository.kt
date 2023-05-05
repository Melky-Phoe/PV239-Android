package cz.muni.packer.repository

import android.content.Context
import cz.muni.packer.data.Categories
import cz.muni.packer.data.Item
import cz.muni.packer.database.item.ItemDao
import cz.muni.packer.database.item.ItemDatabase
import cz.muni.packer.repository.mapper.toAppData
import cz.muni.packer.repository.mapper.toEntity

class ItemRepository (
    context: Context,
    private val dao: ItemDao = ItemDatabase.create(context).ItemDao()
) {

    fun saveOrUpdate(name: String, category: Categories, picture: ByteArray?, currentCount: Int, totalCount: Int, id: Long? = null, packerListId: Long) {
        val item = Item(
            id = id ?: 0,
            name = name,
            category = category,
            picture = picture,
            currentCount = currentCount,
            totalCount = totalCount,
            packerListId = packerListId
        )

        dao.persist(item.toEntity())
    }

    fun updateCount(id: Long, currentCount: Int) {
        dao.updateCount(id = id, count = currentCount)
    }

    fun getAllItems(): List<Item> =
        dao.selectAll()
            .map { it.toAppData() }

    fun getItemsForPackerList(packerListId: Long): List<Item> =
        dao.getItemsForPackerList(packerListId)
            .map { it.toAppData() }

    fun saveAll(data: List<Item>) {
        val entities = data.map { it.toEntity() }
        dao.persist(entities)
    }
}