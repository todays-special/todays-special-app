package com.example.app.RecAdapter

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.app.IngredientData
import com.example.app.MyApplication
import com.example.app.R

class EndCookAdapter(private var recipe_ingredient: MutableList<EndCook>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mainView = itemView.findViewById<ConstraintLayout>(R.id.check_item)
        fun bindItems(item: EndCook) {
            val process = IngredientData().getNameFromId(item.ingerdient)

            val ingred = itemView.findViewById<TextView>(R.id.check_ingredient)
            ingred.text = process

            val used = itemView.findViewById<EditText>(R.id.check_used)
            used.setText(item.usedIn)

            used.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                }

                override fun afterTextChanged(s: Editable?) {
                    val Position: Int = adapterPosition
                    val Changed = itemView.findViewById<EditText>(R.id.check_used).text.toString()
                    recipe_ingredient[Position].usedIn = Changed
                    val getString = recipe_ingredient[Position].usedIn
                    val needChanged = MyApplication.Used
                    needChanged.setString("$Position", "$Changed")
                    val test = MyApplication.Used.getString("$Position","No")
                    Log.d("??","$needChanged")
                    Log.d("change?","$test")
                }
            })
        }


    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int) {

        }
    }

    var itemclick: ItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.check_recycler, parent, false)
        return ItemViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bindItems(recipe_ingredient[position])
        }

    }

    override fun getItemCount() = recipe_ingredient.size

}