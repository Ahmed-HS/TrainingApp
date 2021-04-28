package com.app.training.ui.imageViewer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.app.training.R
import com.app.training.databinding.FragmentImageViewerBinding
import com.app.training.util.setStatusBarColor

class ImageViewerFragment : Fragment(R.layout.fragment_image_viewer) {
    lateinit var binding:FragmentImageViewerBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentImageViewerBinding.bind(requireView())
        val arguments = ImageViewerFragmentArgs.fromBundle(requireArguments())

        requireActivity().setStatusBarColor(binding.root)

        Glide.with(binding.profilePhoto.context)
                .load(arguments.imageURL)
                .into(binding.profilePhoto)
    }
}