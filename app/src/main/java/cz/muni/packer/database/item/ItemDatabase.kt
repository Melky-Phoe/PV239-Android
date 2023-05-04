package cz.muni.packer.database.item

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cz.muni.packer.database.packerlist.PackerListDao
import cz.muni.packer.database.packerlist.PackerListEntity

@Database(
    entities = [ItemEntity::class, PackerListEntity::class],
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
    abstract fun PackerListDao(): PackerListDao
}