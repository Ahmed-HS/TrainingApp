package com.app.training.ui.spendingAnalytics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.training.data.model.Analytic
import com.app.training.data.model.Spending
import com.app.training.data.model.Store
import com.github.mikephil.charting.data.PieEntry
import java.text.DateFormatSymbols
import kotlin.random.Random
import kotlin.random.nextInt

class AnalyticsViewModel : ViewModel() {

    val monthNames = DateFormatSymbols().months.toList()

    private val mMonthAnalytics: MutableList<Analytic> = mutableListOf()

    private val mAnalytics = MutableLiveData<Analytic>()
    val analytics:LiveData<Analytic> = mAnalytics

    var selectedMonthIndex = 0
    private set

    init {
        generateFakeAnalytics()
    }


    fun changeMonth(index:Int)
    {
        selectedMonthIndex = index
        mAnalytics.value = mMonthAnalytics[index]
    }

    private fun generateFakeAnalytics()
    {
        repeat(12)
        {
            val moneySpent = mutableListOf<PieEntry>().apply {
                repeat(6)
                { index ->
                    add(PieEntry(Random.nextInt(100..1000).toFloat(),"Category ${index +1}"))
                }
            }

            val moneyRecieved = mutableListOf<PieEntry>().apply {
                repeat(5)
                { index ->
                    add(PieEntry(Random.nextInt(100..1000).toFloat(),"Category ${index +5}"))
                }
            }

            val storeList = mutableListOf<Store>().apply {
                repeat(6)
                { index ->
                    add(Store("Store${index + 1}","",Random.nextInt(1000..9000).toFloat()))
                }
            }

            val spendingList = mutableListOf<Spending>().apply {
                repeat(6)
                { index ->
                    add(Spending("Category ${index +1}","",Random.nextInt(1000..9000).toFloat(),
                            Random.nextInt(10..99)
                            ))
                }
            }

            val newAnalytic = Analytic(moneySpent,moneyRecieved,storeList,spendingList)

            mMonthAnalytics.add(newAnalytic)

        }
    }
}