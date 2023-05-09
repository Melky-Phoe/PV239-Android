package cz.muni.packer.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import cz.muni.packer.data.PackerList

class PackerListRepository {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference

    fun getPackerLists(callback: (List<PackerList>) -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        database.child("users").child(userId).child("lists").get().addOnSuccessListener { dataSnapshot ->
            val packerLists = dataSnapshot.children.mapNotNull { it.getValue(PackerList::class.java) }
            callback(packerLists)
        }
    }

    fun addPackerList(packerList: PackerList) {
        val userId = auth.currentUser?.uid ?: return
        database.child("users").child(userId).child("lists").push().setValue(packerList)
    }
}

/*
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
}*/
