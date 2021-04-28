package com.app.training.ui.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.app.training.data.model.*
import com.app.training.data.sources.AppDataSource
import com.app.training.di.FakeDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ExerciseViewModel @Inject constructor(
    @FakeDataSource private val dataSource: AppDataSource
) : ViewModel() {

    private val mExerciseList = MutableLiveData<List<Exercise>>()
    val exerciseList: LiveData<List<ExerciseUIModel>> = Transformations.map(mExerciseList){
        exercisesToUIModel(it)
    }

    private val mExerciseFilters = MutableLiveData<Set<String>>()
    val exerciseFilters: LiveData<Set<String>> = mExerciseFilters

    private val mSelectedCenterIndex = MutableLiveData<Int>()
    val selectedCenterIndex:LiveData<Int> = mSelectedCenterIndex
    val selectedCenter:LiveData<Center> = Transformations.map(mSelectedCenterIndex){
        filterExercises(activeFilters)
        centersList.value?.getOrNull(it ?: 0)
    }

    private val mCenterList = MutableLiveData(dataSource.getTrainingCenters())
    val centersList:LiveData<List<Center>> = mCenterList

    var activeFilters:Set<String> = setOf()
    private set

    var activeGenderFilter = Gender.None
    private set

    init {

        val filters  = dataSource.getExerciseFilters()
        activeFilters = filters
        mExerciseFilters.value = filters
        mSelectedCenterIndex.value = mCenterList.value?.isNotEmpty()?.let { 0 }

    }


    private fun mGetSelectedCenter() : Center?
    {
        val centerIndex = mSelectedCenterIndex.value!!
        return centersList.value?.getOrNull(centerIndex)
    }

    fun nextCenter()
    {
        var index = mSelectedCenterIndex.value ?: return
        val lastIndex = centersList.value?.lastIndex!!
        if(index < lastIndex)
        {
            index++
            mSelectedCenterIndex.value = index
        }
    }

    fun previousCenter()
    {
        var index = mSelectedCenterIndex.value ?: return
        if(index >= 1)
        {
            index--
            mSelectedCenterIndex.value = index
        }
    }

    fun setSelectedCenter(index:Int)
    {
        val indices = centersList.value?.indices ?: return
        if(index in indices)
        {
            mSelectedCenterIndex.value = index
        }
    }

    fun filterExercises(filters: Set<String>)
    {
        activeFilters = filters
        val filteredByGender = mGetSelectedCenter()?.exercises?.filterByGender(activeGenderFilter) ?: listOf()
        val filteredByCategory = filteredByGender.filterByCategories(activeFilters)
        mExerciseList.value = filteredByCategory
    }

    fun showAllFilter()
    {
        activeFilters = mExerciseFilters.value ?: setOf()
        val allFilters = mGetSelectedCenter()?.exercises?.filterByCategories(activeFilters) ?: listOf()
        val filteredByGender = allFilters.filterByGender(activeGenderFilter)
        mExerciseList.value = filteredByGender
    }


    fun filterExercisesByGender(genderFilter: Gender)
    {
        activeGenderFilter = genderFilter
        val filteredByGender = mGetSelectedCenter()?.exercises?.filterByGender(activeGenderFilter) ?: listOf()
        val filteredByCategory =  filteredByGender.filterByCategories(activeFilters)
        mExerciseList.value = filteredByCategory
    }

}