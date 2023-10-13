package com.dhanush.nyschoolsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel(){
    val selecteddbn =MutableLiveData<String>()

}