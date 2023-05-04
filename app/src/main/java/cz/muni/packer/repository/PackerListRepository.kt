package cz.muni.packer.repository

import android.content.Context
import cz.muni.packer.data.Item
import cz.muni.packer.data.PackerList
import cz.muni.packer.database.item.ItemDatabase
import cz.muni.packer.database.packerlist.PackerListDao
import kotlin.collections.List
import cz.muni.packer.repository.mapper.toAppData
import cz.muni.packer.repository.mapper.toEntity

class PackerListRepository (
    context: Context,
    private val packerListDao: PackerListDao = ItemDatabase.create(context).PackerListDao(),
    private val itemRepository: ItemRepository = ItemRepository(context)
) {

    fun getAllLists(): List<PackerList> =
        packerListDao.getAllPackerLists().map { packerListEntity ->
            val items = itemRepository.getItemsForPackerList(packerListEntity.id)
            packerListEntity.toAppData(items)
        }

    fun addPackerList(packerList: PackerList): Long {
        val packerListEntity = packerList.toEntity()
        return packerListDao.insert(packerListEntity)
    }

    fun addItemsToPackerList(packerListId: Long, items: List<Item>) {
        items.forEach { item ->
            itemRepository.saveOrUpdate(
                name = item.name,
                category = item.category,
                picture = item.picture,
                currentCount = item.currentCount,
                totalCount = item.totalCount,
                id = item.id,
                packerListId = packerListId
            )
        }
    }
}