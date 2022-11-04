package com.example.app.MainTop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.IngredientData
import com.example.app.R

const val testUrl = "https://img.danawa.com/prod_img/500000/616/833/img/3833616_1.jpg?shrink=330:330&_v=20170329122809"

class MainTopAdapter(private val items: MutableList<MainTop>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mainView = itemView.findViewById<ConstraintLayout>(R.id.item)
        fun bindItems(item: MainTop) {
            //data mapping
            val processedName = IngredientData().getNameFromId(item.name)

            val img = itemView.findViewById<ImageView>(R.id.main_item_img)
            val textView = itemView.findViewById<TextView>(R.id.main_item_text)
            textView.text = processedName

            Glide.with(img)
                .load(testUrl)
                .centerCrop()
                .into(img)
//            Glide.with(img)
//                .load(item.imgUrl)
//                .centerCrop()
//                .into(img)
        }
    }

    interface ItemClick{
        fun onClick(view :View, position: Int)
    }
    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_main_ingredient, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            if(itemClick != null){
                holder.mainView.setOnClickListener{v->
                    itemClick!!.onClick(v, position)
                }
            }
            holder.bindItems(items[position])
        }
    }

    override fun getItemCount() = items.size
}