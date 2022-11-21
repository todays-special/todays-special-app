package com.example.app.shopping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.R

class ShoppingTextAdapter(val items: MutableList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.recommend_item)

        fun bindItems(item: String) {

            name.text = item
        }
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.shopping_item_rv, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            if (itemClick != null) {
                holder.name.setOnClickListener{v->
                    itemClick!!.onClick(v, position)
                }
            }
            holder.bindItems(items[position])
        }
    }

    override fun getItemCount(): Int = items.size
}