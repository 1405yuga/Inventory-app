package com.example.inventory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.data.Item
import com.example.inventory.data.getFormattedPrice
import com.example.inventory.databinding.ItemListItemBinding

class ItemListAdapter(private val onItemClicked : (Item) ->Unit) :ListAdapter<Item,ItemListAdapter.ItemListHolder>(DiffCallBack) {
    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Item>(){
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.itemName == newItem.itemName
            }
        }
    }

    class ItemListHolder(private var binding : ItemListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Item) {
            binding.apply {
                itemName.text = item.itemName
                itemPrice.text = item.getFormattedPrice()
                itemQuantity.text = item.quantityInStock.toString()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListHolder {
        val viewHolder = ItemListHolder(ItemListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: ItemListHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)

    }
}