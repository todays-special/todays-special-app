package com.example.app.refrigerator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.IngredientData
import com.example.app.R
import com.example.app.plusminus.ControlData
import org.w3c.dom.Text

class ExpWarningAdapter(private val items: MutableList<ExpCount>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface ItemClick{
        fun onClick(view :View, position: Int)
    }
    var itemClick : ItemClick? = null
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deleteItem = itemView.findViewById<ImageView>(R.id.imageButton)

        fun bindItems(item: ExpCount) {
            //data mapping
            val processedName = IngredientData().getNameFromId(item.name)
            val name = itemView.findViewById<TextView>(R.id.textView1)
            val exp = itemView.findViewById<TextView>(R.id.exp)
            name.text = processedName
            exp.text = item.exp
            val count = itemView.findViewById<TextView>(R.id.count)
            count.text = item.count
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_status_plus, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            if(itemClick != null){
                holder.deleteItem.setOnClickListener{v->
                    itemClick!!.onClick(v, position)
                }
            }
            holder.bindItems(items[position])
        }
    }

    override fun getItemCount() = items.size
}