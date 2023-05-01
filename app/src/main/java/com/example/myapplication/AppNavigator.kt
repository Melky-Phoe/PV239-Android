package com.example.myapplication

interface AppNavigator {
    fun navigateToItemList()

    fun navigateToItemDetails(item: Item)
}