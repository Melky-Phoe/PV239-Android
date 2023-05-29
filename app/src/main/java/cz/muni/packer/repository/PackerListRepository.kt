package cz.muni.packer.repository

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import cz.muni.packer.data.Item
import cz.muni.packer.data.PackerList

class PackerListRepository {
    private val database: DatabaseReference = Firebase.database.reference
    private val auth = FirebaseAuth.getInstance()

    fun getPackerLists(callback: (List<PackerList>) -> Unit) {
        val userId = auth.currentUser?.uid ?: return

        database.child("users").child(userId).child("lists").addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.children.mapNotNull { it.getValue<PackerList>() }
                callback(value)
                Log.d(TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun addPackerList(packerList: PackerList) {
        val userId = auth.currentUser?.uid ?: return

        val key = database.child("users").child(userId).child("lists").push().key ?: return
        val updatedPackerList = packerList.copy(id = key)

        database.child("users").child(userId).child("lists").child(key).setValue(updatedPackerList)
    }

    fun updatePackerList(packerList: PackerList) {
        val userId = auth.currentUser?.uid ?: return
        if (packerList.id != null) {
            database.child("users").child(userId).child("lists").child(
                packerList.id
            ).setValue(packerList)
        }
    }

    fun getItems(packerListId: String, callback: (List<Item>) -> Unit) {
        val userId = auth.currentUser?.uid ?: return

        database.child("users").child(userId).child("items")
            .addValueEventListener(object : ValueEventListener {

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

    fun deletePackerList(packerListId: String) {
        val userId = auth.currentUser?.uid ?: return

        database.child("users").child(userId).child("lists").child(packerListId).removeValue()
            .addOnSuccessListener {
                // Delete items that have a .packerListId property matching packerListId
                deleteItemsWithPackerListId(packerListId)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Failed to delete packerList with packerListId $packerListId", exception)
            }
    }

    private fun deleteItemsWithPackerListId(packerListId: String) {
        val userId = auth.currentUser?.uid ?: return

        database.child("users").child(userId).child("items")
            .orderByChild("packerListId")
            .equalTo(packerListId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { itemSnapshot ->
                        itemSnapshot.ref.removeValue()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to delete items with packerListId $packerListId", error.toException())
                }
            })
    }
}
