package cz.muni.packer.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import cz.muni.packer.data.Item

class ItemRepository {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference

    fun getItems(packerListId: String, callback: (List<Item>) -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        database.child("users").child(userId).child("lists").child(packerListId).child("items").get().addOnSuccessListener { dataSnapshot ->
            val items = dataSnapshot.children.mapNotNull { it.getValue(Item::class.java) }
            callback(items)
        }
    }

    fun addItem(packerListId: String, item: Item) {
        val userId = auth.currentUser?.uid ?: return
        database.child("users").child(userId).child("lists").child(packerListId).child("items").push().setValue(item)
    }

    fun updateItem(packerListId: String, item: Item) {
        val userId = auth.currentUser?.uid ?: return
        if (item.id != null) {
            database.child("users").child(userId).child("lists").child(packerListId).child("items").child(
                item.id
            ).setValue(item)
        }
    }

    fun updateCount(itemId: String, currentCount: Int) {
        val userId = auth.currentUser?.uid ?: return
        database.child("users").child(userId).child("items").child(itemId).child("currentCount").setValue(currentCount)
    }
}

/*
import android.content.Context
import cz.muni.packer.data.Categories
import cz.muni.packer.data.Item
import cz.muni.packer.database.item.ItemDao
import cz.muni.packer.database.item.ItemDatabase
import cz.muni.packer.repository.mapper.toAppData
import cz.muni.packer.repository.mapper.toEntity

class ItemRepository (
    context: Context,
    private val dao: ItemDao = ItemDatabase.create(context).ItemDao()
) {

    fun saveOrUpdate(name: String, category: Categories, picture: ByteArray?, currentCount: Int, totalCount: Int, id: Long? = null, packerListId: Long) {
        val item = Item(
            id = id ?: 0,
            name = name,
            category = category,
            picture = picture,
            currentCount = currentCount,
            totalCount = totalCount,
            packerListId = packerListId
        )

        dao.persist(item.toEntity())
    }

    fun updateCount(id: Long, currentCount: Int) {
        dao.updateCount(id = id, count = currentCount)
    }

    fun getAllItems(): List<Item> =
        dao.selectAll()
            .map { it.toAppData() }

    fun getItemsForPackerList(packerListId: Long): List<Item> =
        dao.getItemsForPackerList(packerListId)
            .map { it.toAppData() }

    fun saveAll(data: List<Item>) {
        val entities = data.map { it.toEntity() }
        dao.persist(entities)
    }
}*/
