package com.example.app.APIS

data class recipe(
    val name: String,
//    var ingredient : List<String>,
    var Ingredient : List<String>,
    val mainIngredient: String,
    val chief: String,
    val step1: String,
    val step2: String,
    val step3: String,
    val step4: String,
    val step5: String,
    val step6: String,
    val step7: String,
    val step8: String,
    val step9: String,
    val step10: String,
    val step11: String,
    val step12: String,
    val step13: String,
    val step14: String,
    val step15: String,
    val step16: String,
    val step17: String,
    val step18: String,
    val step19: String,
    val step20: String,
    val step21: String,
    val step22: String,
    var link: String,
)

data class recycler(
    var Tname: String,
    var Tcheif: String
)

data class SortMyRecipe(
    var FoodName : String,
    var IngredientRank : Int,
    var MainRank : Int,
)

data class compare(
    val Ingerdients : String
)