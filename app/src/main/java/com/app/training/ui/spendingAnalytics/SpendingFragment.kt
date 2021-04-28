package com.app.training.ui.spendingAnalytics


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.app.training.R
import com.app.training.databinding.FragmentSpendingBinding
import com.app.training.util.MPAndroidChart.bind
import com.app.training.util.setStatusBarColor

class SpendingFragment : Fragment(R.layout.fragment_spending) {

    lateinit var binding: FragmentSpendingBinding

    val analyticsViewModel:AnalyticsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentSpendingBinding.bind(requireView())

        requireActivity().setStatusBarColor(binding.root)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        setupMonthSelector()
        setupStoreAdapter()
        setupSpendingAdapter()
        setupAnalyticsObserver()

    }

    private fun setupAnalyticsObserver()
    {
        analyticsViewModel.analytics.observe(viewLifecycleOwner)
        {
            binding.spentChart.bind("Money Spent"){
                addAll(it.moneySpent)
            }

            binding.receivedChart.bind("Money Received"){
                addAll(it.moneyReceived)
            }

            (binding.favouritesList.adapter as StoreListAdapter).submitList(it.favouritesStores)

            (binding.spendsList.adapter as SpendingListAdapter).submitList(it.spends)

        }
    }

    private fun setupMonthSelector()
    {
        binding.pager.adapter = MonthPagerAdapter().apply {
            submitList(analyticsViewModel.monthNames)
        }

        binding.previousMonth.setOnClickListener {
            binding.pager.previousPage()
        }

        binding.nextMonth.setOnClickListener {
            binding.pager.nextPage()
        }

        binding.pager.currentItem = analyticsViewModel.selectedMonthIndex

        binding.pager.registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback(){
                    override fun onPageSelected(position: Int) {
                        Handler(Looper.getMainLooper()).post {
                            analyticsViewModel.changeMonth(position)
                        }
                    }
                }
        )
    }

    private fun setupStoreAdapter()
    {
        binding.favouritesList.adapter = StoreListAdapter()
    }

    private fun setupSpendingAdapter()
    {
        binding.spendsList.adapter = SpendingListAdapter()
        binding.root
    }

}