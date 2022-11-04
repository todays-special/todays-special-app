package com.example.app.RecAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.app.IngredientData
import com.example.app.R

class EndCookAdapter(private var recipe_ingredient: MutableList<EndCook>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val mainView = itemView.findViewById<ConstraintLayout>(R.id.check_item)
        fun bindItems(item: EndCook){
            val process = IngredientData().getNameFromId(item.ingerdient)

            val ingred = itemView.findViewById<TextView>(R.id.check_ingredient)
            ingred.text = item.ingerdient

            val used = itemView.findViewById<EditText>(R.id.check_used)
            used.setText(item.usedIn)

        }
    }

    interface ItemClick{
        fun onClick(view :View, position: Int)
    }
    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.check_recycler,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EndCookAdapter.ItemViewHolder) {
//            if(itemClick != null){
//                holder.mainView.setOnClickListener{v->
//                    itemClick!!.onClick(v, position)
//                }
//            }
            holder.bindItems(recipe_ingredient[position])
        }
    }

    override fun getItemCount() = recipe_ingredient.size
}