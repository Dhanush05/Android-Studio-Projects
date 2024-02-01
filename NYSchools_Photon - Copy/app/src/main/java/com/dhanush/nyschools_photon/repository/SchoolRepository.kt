package com.dhanush.nyschools_photon.repository

import com.dhanush.nyschools_photon.model.School
import com.dhanush.nyschools_photon.model.SchoolsService
import io.reactivex.Single
import javax.inject.Inject

class SchoolRepository @Inject constructor(private val schoolsService: SchoolsService) {
    fun getSchools() : Single<List<School>> {
        return schoolsService.getSchools()
    }


}