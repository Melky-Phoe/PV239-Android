package com.example.myapplication

interface AppNavigator {
    fun navigateToItemList(packerList: PackerList)

    fun navigateToItemDetails(item: Item)
}