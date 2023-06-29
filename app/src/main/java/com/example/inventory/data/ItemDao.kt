package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: Item)

    @Update
    suspend fun updateItem(item :Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("SELECT * FROM item WHERE id =:id")
    fun getItem(id: String) : Flow<Item>

    @Query("SELECT * FROM item")
    fun getItems() : Flow<List<Item>>
}