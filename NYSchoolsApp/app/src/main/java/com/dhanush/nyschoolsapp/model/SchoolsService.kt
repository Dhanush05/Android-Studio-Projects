package com.dhanush.nyschoolsapp.model
import com.dhanush.nyschoolsapp.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class SchoolsService {

    @Inject
    lateinit var api: SchoolsApi
    init {
        DaggerApiComponent.create().inject(this)
    }
    fun getSchools(): Single<List<School>> {
        return api.getSchools()
    }
    fun getSchoolDetails(dbn: String):Single<List<SchoolDetails>>{
        return api.getSchoolDetails(dbn)
    }

}