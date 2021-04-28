package com.app.training.ui.exercise

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
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
import com.app.training.databinding.DatePopupBinding
import com.app.training.databinding.FragmentExerciseListBinding
import com.app.training.ui.popupList.PopUpListAdapter
import com.app.training.ui.popupList.PopupListItem
import com.app.training.util.dp2Px
import com.app.training.util.onAllChecked
import com.app.training.util.setStatusBarColor
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.overlay.BalloonOverlayAnimation
import com.skydoves.balloon.overlay.BalloonOverlayCircle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseListFragment : Fragment(R.layout.fragment_exercise_list) {

    private lateinit var binding: FragmentExerciseListBinding
    private lateinit var appBarBinding: AppBarBinding

    private val exerciseViewModel: ExerciseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding = FragmentExerciseListBinding.bind(requireView())

        appBarBinding = binding.topBar

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

        requireActivity().setStatusBarColor(appBarBinding.root)

        setUpGenderFilters()

        setUpExerciseAdapter()

        setUpExerciseChips()

        setUpSearchBar()

        setUpCenterSelector()
    }



    private fun setUpCenterSelector()
    {
        appBarBinding.centersBtn.setOnClickListener {
            showCentersPopUp(it)
        }

        exerciseViewModel.centersList.observe(viewLifecycleOwner){
            appBarBinding.centersBtn.isEnabled = it.isNotEmpty()
        }

        appBarBinding.previousCenter.setOnClickListener {
            exerciseViewModel.previousCenter()
        }

        appBarBinding.nextCenter.setOnClickListener { btn ->
            exerciseViewModel.nextCenter()
        }

        exerciseViewModel.selectedCenter.observe(viewLifecycleOwner){
            appBarBinding.centersBtn.text = it?.name ?: "No Centers"
        }
    }


    private fun setUpGenderFilters()
    {
        println("Gender filters")

        exerciseViewModel.activeGenderFilter.let{
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
            exerciseViewModel.filterExercisesByGender(filter)
        }

        appBarBinding.maleBtn.setOnClickListener {
            val filter = if((it as MaterialButton).isChecked) Gender.Male else Gender.None
            exerciseViewModel.filterExercisesByGender(filter)
        }
    }

    private fun setUpSearchBar()
    {
        val searchView = binding.searchBar
        val searchPlateId: Int = searchView.getContext().getResources()
                .getIdentifier("android:id/search_plate", null, null)
        val searchPlate: View = searchView.findViewById(searchPlateId)
        if (searchPlate != null) {
            searchPlate.setBackgroundColor(Color.TRANSPARENT)
            val searchTextId = searchPlate.context.resources.getIdentifier(
                    "android:id/search_src_text",
                    null,
                    null
            )
        }

    }

    private fun setUpExerciseChips()
    {
        exerciseViewModel.exerciseFilters.observe(viewLifecycleOwner){ filters ->
            addExerciseChips(filters)
        }

    }

    private fun addExerciseChips(filters: Set<String>)
    {
        for (filter in filters)
        {
            val newChip = layoutInflater.inflate(
                    R.layout.choice_chip,
                    binding.filterBar.exerciseChips,
                    false
            ) as Chip
            newChip.text = filter
            newChip.typeface = Typeface.DEFAULT_BOLD

            newChip.setOnClickListener {
                filterExercises()
            }
            binding.filterBar.exerciseChips.addView(newChip)

        }

        val allFilter:Chip = binding.filterBar.exerciseChips.findViewById<Chip>(R.id.allFilter)

        allFilter.setOnClickListener {
            exerciseViewModel.showAllFilter()
            clearFilters()
            (it as Chip).isChecked = true
        }

        allFilter.typeface = Typeface.DEFAULT_BOLD

        val activeFiltersLower = exerciseViewModel.activeFilters
                .map { it.toLowerCase() }
                .toSet()

        if(activeFiltersLower.size == exerciseViewModel.exerciseFilters.value?.size)
        {
            allFilter.isChecked = true
            return
        }

        binding.filterBar.exerciseChips.children.forEach {
            val chip = it as Chip
            val filter = chip.text.toString().toLowerCase()
            chip.isChecked = activeFiltersLower.contains(filter)
        }

    }


    private fun clearFilters()
    {
        binding.filterBar.exerciseChips.onAllChecked {
            it.isChecked = false
        }
    }

    private fun filterExercises()
    {
        val allFilter = binding.filterBar.exerciseChips.findViewById<Chip>(R.id.allFilter)
        allFilter.isChecked = false
        val filters:MutableSet<String> = mutableSetOf()
        binding.filterBar.exerciseChips.onAllChecked { chip ->
            filters.add(chip.text.toString())
        }

        if (filters.isEmpty())
        {
            allFilter.isChecked = true
            exerciseViewModel.showAllFilter()
        }
        else
        {
            exerciseViewModel.filterExercises(filters)
        }
    }

    private fun showDatePopUp(anchor: View)
    {
        val datePopup = Balloon.Builder(requireContext())
                .setLayout(R.layout.date_popup)
                .setArrowSize(10)
                .setArrowOrientation(ArrowOrientation.TOP)
                .setArrowPosition(0.2f)
                .setCornerRadius(8f)
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


        val datePoppLayout = datePopup.getContentView().findViewById<View>(R.id.popupRoot)
        val binding = DatePopupBinding.bind(datePoppLayout)
        binding.monday.setOnClickListener {
            // TODO Custom Action
            binding.mondayText.setTextColor(R.attr.colorPrimary)
            Toast.makeText(context, "Your Custom Action", Toast.LENGTH_SHORT).show()
            datePopup.dismiss()
        }

        binding.tuesday.setOnClickListener {
            // TODO Custom Action
            datePopup.dismiss()
        }

        binding.wednesday.setOnClickListener {
            // TODO Custom Action
            datePopup.dismiss()
        }

        datePopup.showAlignBottom(anchor, 100)
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


        val adapter = PopUpListAdapter(exerciseViewModel.selectedCenterIndex.value!!){ item, position ->
            appBarBinding.centersBtn.text = item.title
            exerciseViewModel.setSelectedCenter(position)
            centersPopUp.dismiss()
        }


        adapter.submitList(exerciseViewModel.centersList.value!!.map {
            PopupListItem(it.name,it.exercises.size.toString())
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context).apply {
            scrollToPositionWithOffset(adapter.selectedPos,0)
        }
        centersPopUp.showAlignBottom(anchor, requireContext().dp2Px(60))
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
            }

            override fun onPhotoClick(exercise: Exercise) {
                findNavController().navigate(NavGraphDirections
                        .actionGlobalImageViewerFragment(exercise.imgURL))
            }

        })
        exerciseViewModel.exerciseList.observe(viewLifecycleOwner)
        {
            //println(exerciseViewModel.activeGenderFilter)
            eventListAdapter.submitList(it)
        }
        binding.eventList.adapter = eventListAdapter
    }
}