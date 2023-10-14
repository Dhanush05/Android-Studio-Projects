package com.dhanush.nyschoolsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dhanush.nyschoolsapp.repository.SchoolRepository
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repository: SchoolRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(ListViewModel::class.java)->{
                ListViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailsViewModel::class.java)->{
                DetailsViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Viewmodel class not found")
        }
    }
}
