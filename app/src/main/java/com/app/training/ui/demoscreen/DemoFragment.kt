package com.app.training.ui.demoscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.app.training.R
import com.app.training.databinding.FragmentDemoBinding
import com.app.training.util.setStatusBarColor

class DemoFragment : Fragment(R.layout.fragment_demo) {

    lateinit var binding:FragmentDemoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDemoBinding.bind(requireView())
        requireActivity().setStatusBarColor()

        binding.exerciseListButton.setOnClickListener {
            findNavController().navigate(DemoFragmentDirections.actionDemoFragmentToExerciseListFragment())
        }

        binding.exerciseListAddressButton.setOnClickListener {
            findNavController().navigate(DemoFragmentDirections.actionDemoFragmentToExercisesAddressFragment())
        }

        binding.balanceButton.setOnClickListener {
            findNavController().navigate(DemoFragmentDirections.actionDemoFragmentToDashboardFragment())
        }

        binding.conLostButton.setOnClickListener {
            findNavController().navigate(DemoFragmentDirections.actionDemoFragmentToConnectionLostFragment())
        }

        binding.registerButton.setOnClickListener {
            findNavController().navigate(DemoFragmentDirections.actionDemoFragmentToRegisterFragment())
        }

        binding.trackingButton.setOnClickListener {
            findNavController().navigate(DemoFragmentDirections.actionDemoFragmentToTrackerFragment())
        }

        binding.profileButton.setOnClickListener {
            findNavController().navigate(DemoFragmentDirections.actionDemoFragmentToProfileFragment())
        }

        binding.spendingButton.setOnClickListener {
            findNavController().navigate(DemoFragmentDirections.actionDemoFragmentToSpendingFragment())
        }
    }
}