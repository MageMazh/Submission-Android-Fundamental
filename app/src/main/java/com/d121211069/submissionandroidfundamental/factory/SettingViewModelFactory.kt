package com.d121211069.submissionandroidfundamental.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.d121211069.submissionandroidfundamental.datastore.SettingPreferences
import com.d121211069.submissionandroidfundamental.viewModel.SettingViewModel

class SettingViewModelFactory(private val pref: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}