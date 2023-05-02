package cz.muni.packer.repository.mapper

import cz.muni.packer.data.Categories
import cz.muni.packer.data.Item
import cz.muni.packer.database.ItemEntity

fun ItemEntity.toAppData(): Item =
    Item(
        id = id,
        name = name,
        category = Categories.values().firstOrNull { it.name == category } ?: Categories.OTHER,
        picture = picture,
        currentCount = currentCount,
        totalCount = totalCount
    )

fun Item.toEntity(): ItemEntity =
    ItemEntity(
        id = id,
        name = name,
        category = category.name,
        picture = picture,
        currentCount = currentCount,
        totalCount = totalCount
    )