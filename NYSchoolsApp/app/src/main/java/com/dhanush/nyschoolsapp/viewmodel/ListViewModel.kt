package com.dhanush.nyschoolsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.dhanush.nyschoolsapp.model.School

class ListViewModel {
    val schools = MutableLiveData<List<School>>()
    val schoolLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    private fun fetchSchools(){

    }

}