package com.example.myapplication

import com.example.myapplication.data.Item

interface AppNavigator {
    fun navigateToItemList(packerList: PackerList)

    fun navigateToItemDetails(item: Item)
}