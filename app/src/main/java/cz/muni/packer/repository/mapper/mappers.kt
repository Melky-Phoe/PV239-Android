package cz.muni.packer.repository.mapper

/*
import cz.muni.packer.data.Categories
import cz.muni.packer.data.Item
import cz.muni.packer.data.PackerList
import cz.muni.packer.database.item.ItemEntity
import cz.muni.packer.database.packerlist.PackerListEntity

fun ItemEntity.toAppData(): Item =
    Item(
        id = id,
        name = name,
        category = Categories.values().firstOrNull { it.name == category } ?: Categories.OTHER,
        picture = picture,
        currentCount = currentCount,
        totalCount = totalCount,
        packerListId = packerListId
    )

fun Item.toEntity(): ItemEntity =
    ItemEntity(
        id = id,
        name = name,
        category = category.name,
        picture = picture,
        currentCount = currentCount,
        totalCount = totalCount,
        packerListId = packerListId
    )

fun PackerListEntity.toAppData(items: List<Item>): PackerList =
    PackerList(
        id = id,
        name = name,
        items = items
    )

fun PackerList.toEntity(): PackerListEntity =
    PackerListEntity(
        id = id,
        name = name
    )*/
