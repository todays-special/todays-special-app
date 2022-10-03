package com.example.app.refrigerator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.IngredientData
import com.example.app.R
import com.example.app.localdb.RoomExpDB

class ExpFineAdapter(private val items: MutableList<Exp>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bindItems(item: Exp){
            //data mapping
            val processedName = IngredientData().getNameFromId(item.name)

            val name = itemView.findViewById<TextView>(R.id.textView1)
            val exp = itemView.findViewById<TextView>(R.id.exp)
            name.text = processedName
            exp.text = item.exp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_status_plus, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if( holder is ItemViewHolder){
            holder.bindItems(items[position])
        }
    }

    override fun getItemCount() = items.size
}