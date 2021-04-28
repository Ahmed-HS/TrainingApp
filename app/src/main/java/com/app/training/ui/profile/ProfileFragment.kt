package com.app.training.ui.profile

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.children
import androidx.fragment.app.viewModels
import com.app.training.R
import com.app.training.databinding.FragmentProfileBinding
import com.app.training.util.getFileName
import com.app.training.util.setStatusBarColor
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    lateinit var binding: FragmentProfileBinding
    private val profileViewModel:ProfileViewModel by viewModels()

    val imageChooser = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // Handle the returned Uri
        uri?.let {
            binding.profilePhoto.setImageURI(uri)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentProfileBinding.bind(requireView())

        requireActivity().setStatusBarColor(binding.profileHeader)

        binding.editButton.setOnClickListener {
            toggleFormInput()
        }

        toggleFormInput()

        binding.saveButton.setOnClickListener {
            toggleFormInput()

        }

        setupSpinner()

        binding.profilePhoto.setOnClickListener {
            imageChooser.launch("image/*")
        }

    }

    private fun uploadImage(selectedImageUri:Uri) {
        val contentResolver = requireActivity().contentResolver
        val parcelFileDescriptor =
                contentResolver.openFileDescriptor(selectedImageUri, "r", null) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val fileToUseForUpload = File(requireActivity().cacheDir, contentResolver.getFileName(selectedImageUri))
        val outputStream = FileOutputStream(fileToUseForUpload)
        inputStream.copyTo(outputStream)


    }


    private fun setupSpinner()
    {
        val spinnerAdapter = ArrayAdapter(requireContext(),
                android.R.layout.simple_spinner_item,profileViewModel.spinnerItems).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.dropdown.adapter = spinnerAdapter
    }
    private fun toggleFormInput()
    {
        binding.profileForm.children.forEach {

            it.isEnabled = !it.isEnabled
        }

    }

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
    }
}