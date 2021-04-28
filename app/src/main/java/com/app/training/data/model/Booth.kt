package com.app.training.data.model

data class Booth(
    val name:String,
    val description:String,
    val currentProgress:Int,
    val maxProgress:Int,
    val number:Int,
    val imgURL:String = "https://picsum.photos/200",
)
