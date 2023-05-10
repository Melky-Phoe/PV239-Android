package cz.muni.packer.repository

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import cz.muni.packer.data.Item

class ItemRepository {
    private val database: DatabaseReference = Firebase.database.reference
    private val auth = FirebaseAuth.getInstance()

    fun getItems(packerListId: String, callback: (List<Item>) -> Unit) {
        val userId = auth.currentUser?.uid ?: return

        database.child("users").child(userId).child("items").addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val items = snapshot.children.mapNotNull { it.getValue<Item>() }
                val filteredItems = items.filter { it.packerListId == packerListId }
                callback(filteredItems)
                Log.d(ContentValues.TAG, "Value is: $filteredItems")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun addItem(item: Item) {
        val userId = auth.currentUser?.uid ?: return

        val key = database.child("users").child(userId).child("items").push().key ?: return
        val updatedItem = item.copy(id = key) // Set the auto-generated ID to the Item's id property

        database.child("users").child(userId).child("items").child(key).setValue(updatedItem)
    }

    fun updateItem(item: Item) {
        val userId = auth.currentUser?.uid ?: return
        if (item.id != null) {
            database.child("users").child(userId).child("items").child(
                item.id
            ).setValue(item)
        }
    }

    fun updateCount(itemId: String, currentCount: Int) {
        val userId = auth.currentUser?.uid ?: return
        database.child("users").child(userId).child("items").child(itemId).child("currentCount").setValue(currentCount)
    }
}
