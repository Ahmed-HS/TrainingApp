package com.app.training.data.model

import java.time.ZonedDateTime

data class Expense(
        val title:String,
        val date:ZonedDateTime,
        val amount:Int,
        val phoneNumber:String,
        val imageURL:String,
)
