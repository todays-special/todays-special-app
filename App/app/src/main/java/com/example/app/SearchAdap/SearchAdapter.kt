package com.example.app.SearchAdap

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.IngredientData
import com.example.app.R
import com.example.app.RecAdapter.EndCook
import com.example.app.SearchAdap.SearchResult

class SearchAdapter(private var Search_result: MutableList<SearchResult>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mainView = itemView.findViewById<ConstraintLayout>(R.id.Search_rv)
        fun bindItems(item: SearchResult) {
            val Thumbnail = itemView.findViewById<ImageView>(R.id.Thumbnail)
            Glide.with(Thumbnail)
                .load(item.link)
                .centerCrop()
                .fitCenter()
                .into(Thumbnail)

            val Name = itemView.findViewById<TextView>(R.id.Search_cook_name)
            Name.text = item.name

            val Cheif = itemView.findViewById<TextView>(R.id.Search_cook_cheif)
            Cheif.text = item.cheif

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
            .inflate(R.layout.search_recycler, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchAdapter.ItemViewHolder) {
//            if(itemClick != null){
//                holder.mainView.setOnClickListener{v->
//                    itemClick!!.onClick(v, position)
//                }
//            }
            holder.bindItems(Search_result[position])
        }
    }

    override fun getItemCount() = Search_result.size
}
