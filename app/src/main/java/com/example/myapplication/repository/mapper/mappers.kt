package com.example.myapplication.repository.mapper

import com.example.myapplication.data.Categories
import com.example.myapplication.data.Item
import com.example.myapplication.database.ItemEntity

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