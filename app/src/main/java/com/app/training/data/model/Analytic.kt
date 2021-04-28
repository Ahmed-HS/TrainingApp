package com.app.training.data.model

import com.github.mikephil.charting.data.PieEntry

data class Analytic (
    val moneySpent:List<PieEntry>,
    val moneyReceived:List<PieEntry>,
    val favouritesStores: List<Store>,
    val spends: List<Spending>
)


data class Store(
        val name:String,
        val imageURL:String,
        val amountSpent:Float
)

data class Spending(
        val name:String,
        val imageURL:String,
        val amountSpent:Float,
        val paymentsNumber:Int
)