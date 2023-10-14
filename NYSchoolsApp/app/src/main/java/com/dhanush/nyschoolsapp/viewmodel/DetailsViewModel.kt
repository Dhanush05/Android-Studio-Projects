package com.dhanush.nyschoolsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhanush.nyschoolsapp.di.DaggerApiComponent
import com.dhanush.nyschoolsapp.model.SchoolDetails
import com.dhanush.nyschoolsapp.model.SchoolsService
import com.dhanush.nyschoolsapp.repository.SchoolRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class DetailsViewModel @Inject constructor(
    private val schoolRepository: SchoolRepository
):ViewModel() {
    private val disposable = CompositeDisposable()
    val schooldetails = MutableLiveData<SchoolDetails>()
    val loadError = MutableLiveData<Boolean>()
    val noData = MutableLiveData<Boolean>()

    fun fetchSchoolDetails(data:String){
        disposable.add(schoolRepository.getSchoolDetails(data)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<List<SchoolDetails>>(){
                override fun onSuccess(t: List<SchoolDetails>) {
                    if(t.isNotEmpty()){
                        schooldetails.value  = t[0]
                        noData.value = false
                    }
                    else{
                        noData.value  = true

                    }
                }

                override fun onError(e: Throwable) {
                    loadError.value  = false
                }
            }))
    }
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}