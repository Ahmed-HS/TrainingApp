package com.app.training.data.model


data class Order(
    val name:String,
    val quantity:Int,
    val description: String,
    val imgURL:String = "https://picsum.photos/200"
)