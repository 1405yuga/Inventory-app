package com.example.inventory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.launch

class InventoryViewModel(private val itemDao: ItemDao ) : ViewModel() {

    //insert item in db
    private fun addItem(item: Item){
        viewModelScope.launch {
            itemDao.insertItem(item)
        }
    }

    //new item entry
    private fun getNewItemEntry(itemName : String,itemPrice : String,itemCounts:String) : Item{
        return Item(itemName =itemName, itemPrice = itemPrice.toDouble(), quantityInStock = itemCounts.toInt())
    }

}

class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(InventoryViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("unknown viewmodel class ")
    }
}
