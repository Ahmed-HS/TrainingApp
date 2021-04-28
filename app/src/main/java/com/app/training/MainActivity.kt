package com.app.training


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.training.databinding.ActivityMainBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false
    }


    fun hideBottomBar()
    {
        binding.bottomBar.visibility = View.GONE
    }

    fun showBottomBar()
    {
        binding.bottomBar.visibility = View.GONE
    }
}