package com.app.training.ui.connectionlost

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.training.R
import com.app.training.databinding.FragmentConnectionLostBinding
import com.app.training.util.setStatusBarColor
import com.app.training.util.showErrorSnackBar


class ConnectionLostFragment : Fragment(R.layout.fragment_connection_lost) {

    lateinit var binding: FragmentConnectionLostBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentConnectionLostBinding.bind(requireView())

        binding.settingsButton.setOnClickListener {
            openWifiSettings()
        }

        binding.tryAgainBtn.setOnClickListener {
            if(isOnline())
            {
                findNavController().navigateUp()
            }
            else
            {
               requireView().showErrorSnackBar(getString(R.string.notOnline))
            }
        }
        requireActivity().setStatusBarColor(binding.root)
    }

    private fun openWifiSettings() {
        val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
        }
    }


    fun isOnline(): Boolean {
        val connMgr = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }
}