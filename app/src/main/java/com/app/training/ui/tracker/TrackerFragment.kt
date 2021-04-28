package com.app.training.ui.tracker


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import com.app.training.R
import com.app.training.databinding.FragmentTrackerBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TrackerFragment : Fragment(R.layout.fragment_tracker) {

    lateinit var binding:FragmentTrackerBinding

    private val trackerViewModel:TrackerViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentTrackerBinding.bind(requireView())

        setupBoothAdapter()

        setupOrderListAdapter()

        setupReportsAdapter()

        setupImageActionTiles()

    }


    private fun setupImageActionTiles()
    {

        binding.searchButton.apply {
            iconBackgroundCard.setCardBackgroundColor(requireContext().getColor(R.color.orange_300))
            actionIcon.setImageResource(R.drawable.ic_magnifier)
            actionTitle.text = getString(R.string.sector)
            actionDescription.text = getString(R.string.searchSector)
        }

        binding.boothButton.apply {
            iconBackgroundCard.setCardBackgroundColor(requireContext().getColor(R.color.purple_200))
            actionIcon.setImageResource(R.drawable.ic_add_person)
            actionTitle.text = getString(R.string.booth)
            actionDescription.text = getString(R.string.searchBoothAction)
        }

        binding.messageButton.apply {
            iconBackgroundCard.setCardBackgroundColor(requireContext().getColor(R.color.Green_400))
            actionIcon.setImageResource(R.drawable.ic_message)
            actionTitle.text = getString(R.string.message)
            actionDescription.text = getString(R.string.sendBroadcast)
        }

    }

    private fun setupReportsAdapter()
    {
        val reportsAdapter = ExpensesChartAdapter()

        trackerViewModel.reportsList.observe(viewLifecycleOwner){
            reportsAdapter.submitList(it)
        }

        binding.reportsList.adapter = reportsAdapter

        val dotIndicator = binding.dotIndicator

        dotIndicator.attachToRecyclerView(binding.reportsList)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.reportsList)


    }

    private fun setupBoothAdapter()
    {
        val boothAdapter = BoothListAdapter()

        trackerViewModel.boothList.observe(viewLifecycleOwner){
            boothAdapter.submitList(it)
        }

        binding.boothList.adapter = boothAdapter

    }

    private fun setupOrderListAdapter()
    {
        val ordersAdapter = OrderListAdapter()

        trackerViewModel.ordersList.observe(viewLifecycleOwner){
            ordersAdapter.submitList(it)
        }

        binding.ordersList.adapter = ordersAdapter
    }

}