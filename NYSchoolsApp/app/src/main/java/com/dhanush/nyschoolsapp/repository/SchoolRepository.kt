package com.dhanush.nyschoolsapp.repository

import com.dhanush.nyschoolsapp.model.School
import com.dhanush.nyschoolsapp.model.SchoolDetails
import com.dhanush.nyschoolsapp.model.SchoolsService
import io.reactivex.Single
import javax.inject.Inject

class SchoolRepository @Inject constructor(
    private val schoolsService: SchoolsService
) {
    fun getSchools(): Single<List<School>> {
        return schoolsService.getSchools()
    }
    fun getSchoolDetails(dbn:String): Single<List<SchoolDetails>>{
        return schoolsService.getSchoolDetails(dbn)
    }
}