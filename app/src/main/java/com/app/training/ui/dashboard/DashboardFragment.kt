package com.app.training.ui.dashboard

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.training.R
import com.app.training.data.model.Delivery
import com.app.training.data.model.Expense
import com.app.training.databinding.FragmentDashboardBinding
import com.app.training.util.callPhoneNumber
import com.app.training.util.getColorFromAttr
import com.app.training.util.setStatusBarColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private lateinit var binding: FragmentDashboardBinding
    private val dashboardViewModel:DashboardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDashboardBinding.bind(requireView())

        requireActivity().setStatusBarColor()

        setupDashboardActions()

        setupExpensesList()

        setExpenseSummary(7,false)

        setupOrderTrackerAdpater()
    }

    private fun setupOrderTrackerAdpater()
    {
        binding.deliveryProgress.apply {
            progress = 80
            max = 100
        }

        val orderTrackerAdapter = DeliveryTrackerAdapter(object : DeliveryClickListener{
            override fun onClick(delivery: Delivery) {
                TODO("Not yet implemented")
            }

            override fun onCall(delivery: Delivery) {
                requireContext().callPhoneNumber(delivery.phoneNumber)
            }

        })

        dashboardViewModel.deliveries.observe(viewLifecycleOwner){
            orderTrackerAdapter.submitList(it)
        }

        binding.deliveryList.adapter = orderTrackerAdapter
    }

    private fun setupDashboardActions()
    {
        val actionsAdapter = DashboardActionsAdapter{

        }

        dashboardViewModel.dashboardActions.observe(viewLifecycleOwner){
            actionsAdapter.submitList(it)
        }

        binding.actionList.adapter = actionsAdapter

    }

    private fun setupExpensesList()
    {
        val expensesAdapter = ExpensesListAdapter(object : ExpensesClickListener{
            override fun onClick(expense: Expense) {
            }

            override fun onCall(expense: Expense) {

                requireContext().callPhoneNumber(expense.phoneNumber)
            }

        })

        dashboardViewModel.expenses.observe(viewLifecycleOwner) {
            expensesAdapter.submitList(it)
        }

        binding.expensesList.adapter = expensesAdapter
    }

    private fun setExpenseSummary(percentage:Int, isMore:Boolean)
    {
        val expenseSummaryStart = getString(R.string.expenseSummaryStart)

        val status = if (isMore) getString(R.string.more) else getString(R.string.less)

        val percentageText = SpannableString(" $percentage% $status ").apply {
            setSpan(ForegroundColorSpan(requireContext().getColorFromAttr(R.attr.colorSecondary)),0,length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        val expenseSummaryEnd = getString(R.string.expenseSummaryEnd)
        val builder = SpannableStringBuilder(expenseSummaryStart)
                .append(percentageText)
                .append(expenseSummaryEnd)

        binding.expensesummary.text = builder
    }
}