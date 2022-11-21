package com.example.app.RecAdapter

import android.content.Context
import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
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

class EndCookAdapter(private var recipe_ingredient: MutableList<EndCook>, context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("Ends_used", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()

    }

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
//                    val needChanged = MyApplication.Used
                    setString("$Position", "$Changed")
                    val test = getString("$Position", "No")
//                    Log.d("??","$needChanged")
                    Log.d("change?", "$test")
                }
            })
        }
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.check_recycler, parent, false)
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