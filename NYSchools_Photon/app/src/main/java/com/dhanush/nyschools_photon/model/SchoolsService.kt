package com.dhanush.nyschools_photon.model

import io.reactivex.Single
import javax.inject.Inject

class SchoolsService {
    @Inject
    lateinit var api: SchoolsApi
    init{
        //DaggerApiComponent.create().inject(this)
    }
    fun getSchools(): Single<List<School>> = api.getSchools()
}