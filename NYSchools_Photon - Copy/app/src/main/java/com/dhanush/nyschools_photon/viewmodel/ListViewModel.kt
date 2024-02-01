package com.dhanush.nyschools_photon.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhanush.nyschools_photon.model.School
import com.dhanush.nyschools_photon.repository.SchoolRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel @Inject constructor(private val schoolRepository: SchoolRepository): ViewModel() {
    val schools = MutableLiveData<List<School>>()
    val schoolsLoadError = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()

    fun refresh(){ //this function will  be called in the MainActivity
        fetchSchools()
    }
    private fun fetchSchools() {
        disposable.add(schoolRepository.getSchools()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<List<School>>(){
                override fun onSuccess(t:List<School>){
                    schools.value = t
                    schoolsLoadError.value = false
                }
                override fun onError(e: Throwable){
                    schoolsLoadError.value = true
                }
            })
        )
    }
    override  fun onCleared(){
        super.onCleared()
        disposable.clear()
    }
}