package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ItemEntity::class],
    version = 1
)
abstract class ItemDatabase : RoomDatabase() {

    companion object {
        private const val NAME = "item.db"

        fun create(context: Context): ItemDatabase =
            Room.databaseBuilder(context, ItemDatabase::class.java, NAME)
                .allowMainThreadQueries()
                .build()
    }

    abstract fun ItemDao(): ItemDao
}