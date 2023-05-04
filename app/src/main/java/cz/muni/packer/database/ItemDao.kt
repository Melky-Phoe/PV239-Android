package cz.muni.packer.database

import androidx.room.*

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun persist(entity: ItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun persist(entity: List<ItemEntity>)

    @Query("SELECT * FROM ItemEntity")
    fun selectAll(): List<ItemEntity>

    @Update()
    fun updateItem(entity: ItemEntity) // not prefered

    @Query("UPDATE ItemEntity SET currentCount = :count WHERE id = :id")
    fun updateCount(count: Int, id: Long) // Myslim ze by byl nejrychlejsi, ale nefunguje
}