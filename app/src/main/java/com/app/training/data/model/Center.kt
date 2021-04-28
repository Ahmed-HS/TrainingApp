package com.app.training.data.model

data class Center(
    val id:Int,
    val name:String,
    val count:Int,
    val exercises:List<Exercise>,
)