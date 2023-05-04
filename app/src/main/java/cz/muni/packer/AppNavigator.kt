package cz.muni.packer

import cz.muni.packer.data.Item
import cz.muni.packer.data.PackerList

interface AppNavigator {
    fun navigateToItemList(packerList: PackerList)

    fun navigateToItemDetails(item: Item)
}