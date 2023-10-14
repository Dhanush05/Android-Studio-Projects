package com.dhanush.nyschoolsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhanush.nyschoolsapp.di.DaggerApiComponent
import com.dhanush.nyschoolsapp.model.School
import com.dhanush.nyschoolsapp.model.SchoolsService
import com.dhanush.nyschoolsapp.repository.SchoolRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val schoolRepository: SchoolRepository
): ViewModel() {
    val schools = MutableLiveData<List<School>>()
    val schoolLoadError = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()

    fun refresh(){
        fetchSchools()
    }
    private fun fetchSchools(){
        disposable.add(schoolRepository.getSchools()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<School>>(){
                override fun onSuccess(t: List<School>) {
                    schools.value = t
                    schoolLoadError.value = false
                }
                override fun onError(e: Throwable) {
                    schoolLoadError.value  =true
                }
            }))

    }
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}