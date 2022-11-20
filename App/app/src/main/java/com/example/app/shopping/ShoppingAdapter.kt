package com.example.app.shopping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.app.R

class ShoppingAdapter(val items: MutableList<ShoppingView>) :
    RecyclerView.Adapter<ViewHolder>() {

    inner class ItemViewHolder(itemView: View) : ViewHolder(itemView) {
        fun bindItems(item: ShoppingView) {

            val name = itemView.findViewById<TextView>(R.id.name)
            val iv = itemView.findViewById<ImageView>(R.id.img)
            val madeIn = itemView.findViewById<TextView>(R.id.made_in)
            val price = itemView.findViewById<TextView>(R.id.price)

            Glide.with(iv)
                .load(item.imgUrl)
                .centerCrop()
                .into(iv)

            val str = item.name.replace("<b></b>","")
            val str1 = str.replace("<b>"," ")
            val str2 = str1.replace("</b>"," ")
            name.text = str2
            madeIn.text = item.madeIn
            price.text = item.price +"원"

        }
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_shopping_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            if (itemClick != null) {
//                holder.deleteItem.setOnClickListener{v->
//                    itemClick!!.onClick(v, position)
//                }
            }
            holder.bindItems(items[position])
        }
    }

    override fun getItemCount(): Int = items.size
}