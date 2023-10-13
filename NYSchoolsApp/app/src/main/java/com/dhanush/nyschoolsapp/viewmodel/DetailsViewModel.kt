package com.dhanush.nyschoolsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhanush.nyschoolsapp.di.DaggerApiComponent
import com.dhanush.nyschoolsapp.model.SchoolDetails
import com.dhanush.nyschoolsapp.model.SchoolsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class DetailsViewModel:ViewModel() {
    private val disposable = CompositeDisposable()
    val schooldetails = MutableLiveData<SchoolDetails>()
    val loadError = MutableLiveData<Boolean>()

    @Inject
    lateinit var schoolsService: SchoolsService
    init{
        DaggerApiComponent.create().inject(this)
    }

    fun fetchSchoolDetails(data:String){
        disposable.add(schoolsService.getSchoolDetails(data)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<List<SchoolDetails>>(){
                override fun onSuccess(t: List<SchoolDetails>) {
                    schooldetails.value = t.get(0)
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