package com.app.training.ui.exercise.withAddress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.app.training.data.model.*
import com.app.training.data.sources.AppDataSource
import com.app.training.di.FakeDataSource
import com.app.training.ui.exercise.ExerciseUIModel
import com.app.training.ui.exercise.exercisesToUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExercisesAddressViewModel @Inject constructor(
        @FakeDataSource private val dataSource:AppDataSource)
    : ViewModel() {
    private val mExerciseList = MutableLiveData<List<Exercise>>()
    val exerciseList: LiveData<List<ExerciseUIModel>> = Transformations.map(mExerciseList){
        exercisesToUIModel(it)
    }


    private val mSelectedCenterIndex = MutableLiveData<Int>()
    val selectedCenterIndex: LiveData<Int> = mSelectedCenterIndex
    val selectedCenter: LiveData<Center> = Transformations.map(mSelectedCenterIndex){
        //resetSelectedAddress()
        selectedAddress = getSelectedCenterAddress().entries
                .firstOrNull()?.key ?: ""

        filterExercisesByGender(activeGenderFilter)
        centersList.value?.getOrNull(it ?: 0)
    }

    private val mCenterList = MutableLiveData(dataSource.getTrainingCenters())
    val centersList:LiveData<List<Center>> = mCenterList


    var selectedAddress:String = ""
    private set



    var activeGenderFilter = Gender.None
    private set

    init {

        mSelectedCenterIndex.value = mCenterList.value?.isNotEmpty()?.let { 0 }
    }

    fun filterByAddress(address:String)
    {
        selectedAddress = address
        val filteredByAddress = mGetSelectedCenter()?.exercises?.filterByAddress(address) ?: listOf()
        val filteredByGender = filteredByAddress.filterByGender(activeGenderFilter)
        mExerciseList.value = filteredByGender
    }


    private fun mGetSelectedCenter() : Center?
    {
        val centerIndex = mSelectedCenterIndex.value!!
        return centersList.value?.getOrNull(centerIndex)
    }

    fun getSelectedCenterAddress() : Map<String,Int>
    {
        return mGetSelectedCenter()?.exercises
                ?.groupBy { it.address }
                ?.entries
                ?.associate { it.key to it.value.filterByGender(activeGenderFilter).size }
                ?: mapOf()
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


    fun filterExercisesByGender(genderFilter: Gender)
    {
        activeGenderFilter = genderFilter
        val filteredByGender = mGetSelectedCenter()?.exercises?.filterByGender(activeGenderFilter) ?: listOf()
        val filteredByAddress =  filteredByGender.filterByAddress(selectedAddress)
        mExerciseList.value = filteredByAddress
    }

}