package com.dhanush.countries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhanush.countries.model.CountriesService
import com.dhanush.countries.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel:ViewModel() {
    val  countries =  MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    private val countriesService = CountriesService()
    private val disposable = CompositeDisposable()
    fun refresh(){
        fetchCountries()
    }

    private fun fetchCountries() {
        disposable.add(countriesService.getCountries()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                override fun onSuccess(t: List<Country>) {
                    countries.value = t
                    loading.value = false
                    countryLoadError.value = false
                }
                override fun onError(e: Throwable) {
                    loading.value = false
                    countryLoadError.value = true
                }

            }))

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}