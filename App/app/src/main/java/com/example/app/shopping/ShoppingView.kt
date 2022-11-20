package com.example.app.shopping

import java.io.Serializable

data class ShoppingView(
    val name : String,
    val price : String,
    val imgUrl: String,
    val madeIn: String,
    val link: String
): Serializable