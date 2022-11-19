package com.example.app.shopping

data class shoppingDC(
    val lastBuildDate :String,
    val total: String,
    val start: String,
    val display: String,
    val items: List<ItemSet>
)

data class ItemSet(
    val title: String,
    val link: String,
    val image: String,
    val lprice: String,
    val hprice: String,
    val mallName: String,
    val productId: String,
    val productType: String,
    val brand: String,
    val maker: String,
    val category1: String,
    val category2: String,
    val category3: String,
    val category4: String,
)