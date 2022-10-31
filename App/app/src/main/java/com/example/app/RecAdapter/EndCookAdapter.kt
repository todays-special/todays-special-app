package com.example.app.RecAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R

class EndCookAdapter(private val recipe_ingredient: MutableList<EndCook>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val mainView = itemView.findViewById<ConstraintLayout>(R.id.check_item)
        fun bindItems(item: EndCook){

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.check_recycler,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount() = recipe_ingredient.size
}