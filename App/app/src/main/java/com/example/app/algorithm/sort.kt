package com.example.app.algorithm

import android.util.Log
import com.example.app.APIS.recipe
import com.example.app.R

class sort {
    public fun orderByIngredient(items: MutableList<recipe>, ingredient: List<String>): MutableList<recipe> {
//        for (i in items.indices) {
//            val itemIngredient = items[i].Ingredient
//            val matchCounter = itemIngredient.count() - itemIngredient.minus(ingredient.toSet()).count()
//            items[i].matchCount = matchCounter
//        }

        return items.map {
            it.matchCount = it.Ingredient.count() - it.Ingredient.minus(ingredient.toSet()).count()
            it
        }.sortedBy { it.matchCount }.reversed() as MutableList<recipe>
    }
}