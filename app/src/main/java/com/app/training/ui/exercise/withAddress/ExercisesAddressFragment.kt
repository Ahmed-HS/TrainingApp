package com.app.training.ui.exercise.withAddress

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.training.NavGraphDirections
import com.app.training.R
import com.app.training.data.model.Exercise
import com.app.training.data.model.Gender
import com.app.training.databinding.AppBarBinding
import com.app.training.databinding.FragmentExerciseAddressBinding
import com.app.training.ui.exercise.*
import com.app.training.ui.popupList.PopUpListAdapter
import com.app.training.ui.popupList.PopupListItem
import com.app.training.util.dp2Px
import com.app.training.util.setStatusBarColor
import com.google.android.material.button.MaterialButton
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.overlay.BalloonOverlayAnimation
import com.skydoves.balloon.overlay.BalloonOverlayCircle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExercisesAddressFragment : Fragment(R.layout.fragment_exercise_address) {

    private lateinit var binding: FragmentExerciseAddressBinding
    private lateinit var appBarBinding: AppBarBinding

    private val exerciseAddressesViewModel: ExercisesAddressViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentExerciseAddressBinding.bind(requireView())

        appBarBinding = binding.topBar

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

        requireActivity().setStatusBarColor(appBarBinding.root)

        setUpGenderFilters()

        setUpExerciseAdapter()

        setUpCenterSelector()

        binding.addressFab.setOnClickListener {
            showAddressesPopUp(it)
        }
    }

    private fun setUpCenterSelector()
    {
        appBarBinding.centersBtn.setOnClickListener {
            showCentersPopUp(it)
        }

        exerciseAddressesViewModel.centersList.observe(viewLifecycleOwner){
            appBarBinding.centersBtn.isEnabled = it.isNotEmpty()
        }

        appBarBinding.previousCenter.setOnClickListener {
            exerciseAddressesViewModel.previousCenter()
        }

        appBarBinding.nextCenter.setOnClickListener {
            exerciseAddressesViewModel.nextCenter()
        }

        exerciseAddressesViewModel.selectedCenter.observe(viewLifecycleOwner){
            appBarBinding.centersBtn.text = it?.name ?: "No Center"
            binding.addressFab.isEnabled = it?.exercises?.isNullOrEmpty()?.not() ?: false
        }
    }

    private fun setUpGenderFilters()
    {
        exerciseAddressesViewModel.activeGenderFilter.let {
            if(it == Gender.Female)
            {
                (appBarBinding.femaleBtn as MaterialButton).isChecked = true
            }
            else if(it == Gender.Male)
            {
                (appBarBinding.maleBtn as MaterialButton).isChecked = true
            }
        }

        appBarBinding.femaleBtn.setOnClickListener {
            val filter = if((it as MaterialButton).isChecked) Gender.Female else Gender.None
            exerciseAddressesViewModel.filterExercisesByGender(filter)
        }

        appBarBinding.maleBtn.setOnClickListener {
            val filter = if((it as MaterialButton).isChecked) Gender.Male else Gender.None
            exerciseAddressesViewModel.filterExercisesByGender(filter)
        }
    }



    private fun showCentersPopUp(anchor: View)
    {
        val recyclerView = RecyclerView(requireContext())
        val centersPopUp = Balloon.Builder(requireContext())
                .setLayout(recyclerView)
                .setCornerRadius(8f)
                .setArrowSize(0)
                //.setWidth(250)
                .setHeight(300)
                .setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.surfaceColorLight))
                .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                .setLifecycleOwner(viewLifecycleOwner)
                .setIsVisibleOverlay(true) // sets the visibility of the overlay for highlighting an anchor.
                .setOverlayColorResource(R.color.overlayLight) // background color of the overlay using a color resource.
                .setOverlayPadding(6f) // sets a padding value of the overlay shape internally.
                .setBalloonOverlayAnimation(BalloonOverlayAnimation.FADE) // default is fade.
                .setDismissWhenOverlayClicked(false)
                .setOverlayShape(BalloonOverlayCircle(radius = 0f))
                .build()//


        val adapter = PopUpListAdapter(exerciseAddressesViewModel.selectedCenterIndex.value!!){ center, position ->
            appBarBinding.centersBtn.text = center.title
            exerciseAddressesViewModel.setSelectedCenter(position)
            centersPopUp.dismiss()
        }


        adapter.submitList(exerciseAddressesViewModel.centersList.value?.map {
            PopupListItem(it.name,it.exercises.size.toString())
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context).apply {
            scrollToPositionWithOffset(adapter.selectedPos,0)
        }
        centersPopUp.showAlignBottom(anchor, requireContext().dp2Px(60))
    }

    private fun showAddressesPopUp(anchor: View)
    {
        val recyclerView = RecyclerView(requireContext())
        val centersPopUp = Balloon.Builder(requireContext())
                .setLayout(recyclerView)
                .setCornerRadius(8f)
                .setArrowSize(0)
                .setWidth(250)
                .setHeight(200)
                .setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.surfaceColorLight))
                .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                .setLifecycleOwner(viewLifecycleOwner)
                .setIsVisibleOverlay(true) // sets the visibility of the overlay for highlighting an anchor.
                .setOverlayColorResource(R.color.overlayLight) // background color of the overlay using a color resource.
                .setOverlayPadding(6f) // sets a padding value of the overlay shape internally.
                .setBalloonOverlayAnimation(BalloonOverlayAnimation.FADE) // default is fade.
                .setDismissWhenOverlayClicked(false)
                .setOverlayShape(BalloonOverlayCircle(radius = 0f))
                .build()//


        val adresses = exerciseAddressesViewModel.getSelectedCenterAddress()
        val selectedAddress = exerciseAddressesViewModel.selectedAddress
        val adapter = PopUpListAdapter(adresses.keys.indexOf(selectedAddress)){ item, position ->
            //exerciseAddressesViewModel.selectedAddressIndex = position
            exerciseAddressesViewModel.filterByAddress(item.title)
            centersPopUp.dismiss()
        }


        adapter.submitList(adresses.map {
            PopupListItem(it.key,it.value.toString())
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context).apply {
            scrollToPositionWithOffset(adapter.selectedPos,0)
        }
        centersPopUp.showAlignTop(anchor)
    }


    private fun setUpExerciseAdapter()
    {
        val eventListAdapter = ExerciseListAdapter(object : ExerciseClickListener {
            override fun onShare(exercise: Exercise) {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, exercise.title)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

            override fun onJoin(exercise: Exercise) {
                //Toast.makeText(requireContext(), "Joined", Toast.LENGTH_SHORT).show()
                //findNavController().navigate(ExerciseListFragmentDirections.actionExerciseListFragmentToRegisterFragment())
            }

            override fun onPhotoClick(exercise: Exercise) {
                findNavController().navigate(NavGraphDirections
                        .actionGlobalImageViewerFragment(exercise.imgURL))
            }

        })
        exerciseAddressesViewModel.exerciseList.observe(viewLifecycleOwner)
        {
            eventListAdapter.submitList(it)
        }
        binding.eventList.adapter = eventListAdapter
    }
}