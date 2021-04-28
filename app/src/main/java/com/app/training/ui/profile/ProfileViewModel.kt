package com.app.training.ui.profile

import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel(){

    val spinnerItems: List<String> = (1..5).map { "Option $it" }

}