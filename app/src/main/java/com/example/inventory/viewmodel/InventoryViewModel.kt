package com.example.inventory.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.launch

class InventoryViewModel(private val itemDao: ItemDao) : ViewModel() {

    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

    //insert item in db
    private fun addItem(item: Item) {
        viewModelScope.launch {
            itemDao.insertItem(item)
        }
    }

    //new item entry
    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCounts: String): Item {
        return Item(
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCounts.toInt()
        )
    }

    //add new item in db
    public fun addNewItem(itemName: String, itemPrice: String, itemCounts: String) {
        addItem(getNewItemEntry(itemName, itemPrice, itemCounts))
    }

    //getting an item
    public fun retrieveItem(id: Int): LiveData<Item> {
        return itemDao.getItem(id.toString()).asLiveData()
    }

    //updating values
    public fun updateItem(item: Item) {
        viewModelScope.launch {
            itemDao.updateItem(item)
        }
    }

    public fun sellItem(item: Item) {
        if (item.quantityInStock > 0) {
            //decrease quantity by 1
            //copy function creates the same item by changing certain properties
            val newItem = item.copy(quantityInStock = item.quantityInStock - 1)
            updateItem(newItem)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.deleteItem(item)
        }
    }

    private fun getUpdatedItemEntry(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ): Item {
        return Item(
            id = itemId,
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }

    fun updateItem(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ) {
        val updatedItem = getUpdatedItemEntry(itemId,itemName,itemPrice,itemCount)
        updateItem(updatedItem)
    }

    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }

    fun isStockAvailable(item: Item): Boolean {
        return item.quantityInStock > 0
    }

}

class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("unknown viewmodel class ")
    }
}
