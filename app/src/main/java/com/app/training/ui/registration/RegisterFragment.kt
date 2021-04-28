package com.app.training.ui.registration

import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.training.R
import com.app.training.databinding.FragmentRegisterBinding
import com.app.training.util.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class RegisterFragment : Fragment(R.layout.fragment_register) {

    lateinit var binding:FragmentRegisterBinding

    val imageChooser = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // Handle the returned Uri
        uri?.let {
            binding.registerPhoto.setImageURI(uri)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentRegisterBinding.bind(view)
        requireActivity().setStatusBarColor(binding.root)

        binding.verifyButton.setOnClickListener {
            if(!isValidRegistration())
            {
                requireView().showErrorSnackBar("Invalid data")
            }
        }

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.uploadButton.setOnClickListener {
            imageChooser.launch("image/*")
        }

        setupEditTexts()
    }

    private fun isValidRegistration() : Boolean
    {
        binding.root.children.forEach {
            if(it is TextInputEditText && it.text.isNullOrEmpty())
            {
                return false
            }
        }

        return binding.email.editText?.text.isValidEmail() &&
                binding.mobileNumber.editText?.text.isValidPhone()
    }

    private fun setupEditTexts()
    {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_enabled),

            )

        val colors = intArrayOf(
            requireContext().getColorFromAttr(R.attr.textOnSurfaceDark),
            Color.RED,
            Color.GREEN,
            Color.BLUE
        )

        val myList = ColorStateList(states, colors)

        binding.root.children.forEach {
            if(it is TextInputLayout)
            {
                it.setHintTextAppearance(R.style.TrainingApp_TextAppearance_EditTextHint)
                it.defaultHintTextColor = myList
            }
        }
    }
}