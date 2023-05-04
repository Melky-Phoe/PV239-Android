package cz.muni.packer.database.packerlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.muni.packer.database.item.ItemEntity

@Dao
interface PackerListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(packerList: PackerListEntity): Long

    @Query("SELECT * FROM PackerListEntity")
    fun getAllPackerLists(): List<PackerListEntity>

    @Query("SELECT * FROM ItemEntity WHERE packerListId = :packerListId")
    fun getItemsForPackerList(packerListId: Long): List<ItemEntity>
}
