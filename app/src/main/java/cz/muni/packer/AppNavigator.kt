package cz.muni.packer

import cz.muni.packer.data.Item

interface AppNavigator {
    fun navigateToItemList(packerList: PackerList)

    fun navigateToItemDetails(item: Item)
}