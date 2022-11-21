package com.example.app.SearchAdap

import android.annotation.SuppressLint
import android.content.Context
import android.icu.util.TimeUnit.values
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.IngredientData
import com.example.app.R
import com.example.app.RecAdapter.EndCook
import com.example.app.SearchAdap.SearchResult
import java.time.chrono.JapaneseEra.values

class SearchAdapter(private var Search_result: ArrayList<SearchResult>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var TAG = "SearchAdapter"
    var filtered = ArrayList<SearchResult>()
    var itemFliter = ItemFilter()

    init {
        filtered.addAll(Search_result)
    }

    inner class ItemFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterString = charSequence.toString()
            val results = FilterResults()

            //검색이 필요없을 경우를 위해 원본 배열을 복제
            val filteredList: ArrayList<SearchResult> = ArrayList<SearchResult>()
            //공백제외 아무런 값이 없을 경우 -> 원본 배열
            if (filterString.trim { it <= ' ' }.isEmpty()) {
                results.values = Search_result
                results.count = Search_result.size
                return results
                //공백제외 2글자 이하인 경우 -> 이름으로만 검색
            }
//            else if (filterString.trim { it <= ' ' }.length <= 2) {
//                for (person in Search_result) {
//                    if (person.name.contains(filterString)) {
//                        filtered.add(person)
//                    }
//                }
//                //그 외의 경우(공백제외 2글자 초과) -> 이름/전화번호로 검색
//            }
            else {
                for (person in Search_result) {
                    if (person.name.contains(filterString) || person.cheif.contains(
                            filterString
                        )
                    ) {
                        filteredList.add(person)
                    }
                }
            }
            results.values = filteredList
            results.count = filteredList.size

            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults) {
            filtered.clear()
            filtered.addAll(filterResults.values as ArrayList<SearchResult>)
            notifyDataSetChanged()
        }
    }


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mainView = itemView.findViewById<ConstraintLayout>(R.id.search_rv_item)
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

    var itemClicks: ItemClick? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.searcg_recycler, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchAdapter.ItemViewHolder) {
            if (itemClicks != null) {
                holder.mainView.setOnClickListener { v ->
                    itemClicks!!.onClick(v, position)
                }
            }
            holder.bindItems(Search_result[position])
        }
    }

    override fun getItemCount() = Search_result.size

    override fun getFilter(): Filter {
        return itemFliter
    }
}